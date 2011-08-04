(ns dod-v3
  (:use lazy)
  (:use svg)
  (:use web-server))

(def num-players 2)
(def max-dice 3)
(def board-size 5)
(def board-hexnum (* board-size board-size))
(def ai-level 4)

(def board-width 900)
(def board-height 500)
(def board-scale 64)
(def top-offset 3)
(def dice-scale 40)
(def dot-size 0.05)
(def die-colors [[255 63 63] [63 63 255]])

(def *cur-game-tree* (atom nil))
(def *from-tile* (atom nil))

(defn indexed [coll]
  (map list (range) coll))

(defn third [coll]
  (first (rest (rest coll))))

(defn gen-board []
  (let [f (fn [] [(rand-int num-players) (inc (rand-int max-dice))])]
    (vec (repeatedly board-hexnum f))))

(defn player-letter [n]
  (char (+ 97 n)))

(defn draw-board [board]
  (doseq [y (range board-size)]
    (print (apply str (repeat (- board-size y) \space)))
    (doseq [x (range board-size)]
      (let [[player dice] (board (+ x (* board-size y)))]
        (print (str (player-letter player) "-" dice " "))))
    (newline)))

(defn neighbors [pos]
  (let [up (- pos board-size)
        down (+ pos board-size)]
    (for [p (concat [up down]
                    (when-not (zero? (rem pos board-size))
                      [(dec up) (dec pos)])
                    (when-not (zero? (rem (inc pos) board-size))
                      [(inc pos) (inc down)]))
          :when (< -1 p board-hexnum)]
      p)))

(def neighbors (memoize neighbors))

(defn board-attack [board player src dst dice]
  (assoc board
    src [player 1]
    dst [player (dec dice)]))

(defn add-new-dice [board player spare-dice]
  (let [f (fn [[[cur-player cur-dice] :as lst] n acc]
            (cond (zero? n) (into acc lst)
                  (empty? lst) acc
                  :else (if (and (= cur-player player)
                                 (< cur-dice max-dice))
                          (recur (rest lst)
                                 (dec n)
                                 (conj acc [cur-player (inc cur-dice)]))
                          (recur (rest lst)
                                 n
                                 (conj acc [cur-player cur-dice])))))]
    (f board spare-dice [])))

(declare game-tree add-passing-move attacking-moves)

(defn game-tree [board player spare-dice first-move?]
  [player
   board
   (add-passing-move board
                     player
                     spare-dice
                     first-move?
                     (attacking-moves board player spare-dice))])

(def game-tree (memoize game-tree))

(defn add-passing-move [board player spare-dice first-move? moves]
  (if first-move?
    moves
    (cons [nil
           (game-tree (add-new-dice board player (dec spare-dice))
                      (rem (inc player) num-players)
                      0
                      true)]
          moves)))

(defn attacking-moves [board cur-player spare-dice]
  (let [player (fn [pos] (first (board pos)))
        dice (fn [pos] (second (board pos)))]
    (mapcat1 (fn [src]
               (when (= cur-player (player src))
                 (mapcat1 (fn [dst]
                            (when (and (not= cur-player (player dst))
                                       (> (dice src) (dice dst)))
                              [[[src dst]
                                (game-tree (board-attack board
                                                         cur-player
                                                         src
                                                         dst
                                                         (dice src))
                                           cur-player
                                           (+ spare-dice (dice dst))
                                           false)]]))
                          (neighbors src))))
             (range board-hexnum))))

(defn print-info [tree]
  (println "current player =" (player-letter (first tree)))
  (draw-board (second tree)))

(defn handle-human [tree]
  (println "choose your move:")
  (let [moves (third tree)]
    (doseq [[n move] (indexed moves)]
      (let [action (first move)]
        (println (str (inc n) ".")
                 (if action
                   (str (first action) " -> " (second action))
                   "end turn"))))
    (second (nth moves (dec (read))))))

(defn winners [board]
  (let [tally (map first board)
        totals (frequencies tally)
        best (apply max (vals totals))]
    (map first (filter (fn [[player total]] (= best total)) totals))))

(defn announce-winners [board]
  (let [ws (winners board)]
    (if (> (count ws) 1)
      (println (str "The winners are " (map player-letter ws)))
      (println (str "The winner is " (player-letter (first ws)))))))

(defn play-vs-human [tree]
  (print-info tree)
  (if (seq (third tree))
    (recur (handle-human tree))
    (announce-winners (second tree))))

(defn threatened? [pos board]
  (let [[player dice] (board pos)]
    (some (fn [n]
            (let [[nplayer ndice] (board n)]
              (and (not= player nplayer) (> ndice dice))))
          (neighbors pos))))

(defn score-board [board player]
  (reduce + (for [[pos hex] (indexed board)]
              (if (= player (first hex))
                (if (threatened? pos board)
                  1
                  2)
                -1))))

(declare ab-get-ratings-max ab-get-ratings-min ab-rate-position)

(defn ab-get-ratings-max [tree player upper-limit lower-limit]
  (let [f (fn f [moves lower-limit]
            (when (seq moves)
              (let [x (ab-rate-position (second (first moves))
                                        player
                                        upper-limit
                                        lower-limit)]
                (if (>= x upper-limit)
                  [x]
                  (cons x (f (rest moves) (max x lower-limit)))))))]
    (f (third tree) lower-limit)))

(defn ab-get-ratings-min [tree player upper-limit lower-limit]
  (letfn [(f [moves upper-limit]
            (when (seq moves)
              (let [x (ab-rate-position (second (first moves))
                                        player
                                        upper-limit
                                        lower-limit)]
                (if (<= x lower-limit)
                  [x]
                  (cons x (f (rest moves) (min x upper-limit)))))))]
    (f (third tree) upper-limit)))

(defn ab-rate-position [tree player upper-limit lower-limit]
  (let [moves (third tree)]
    (if (seq moves)
      (if (= player (first tree))
        (apply max (ab-get-ratings-max tree
                                       player
                                       upper-limit
                                       lower-limit))
        (apply min (ab-get-ratings-min tree
                                       player
                                       upper-limit
                                       lower-limit)))
      (score-board (second tree) player))))

(defn limit-tree-depth [tree depth]
  [(first tree)
   (second tree)
   (when (pos? depth)
     (map1 (fn [move]
             [(first move)
              (limit-tree-depth (second move) (dec depth))])
           (third tree)))])

(defn handle-computer [tree]
  (let [ratings (ab-get-ratings-max (limit-tree-depth tree ai-level)
                                    (first tree)
                                    Integer/MAX_VALUE
                                    Integer/MIN_VALUE)
        idx-best (first (reduce (fn [x y]
                                  (if (>= (second x) (second y))
                                    x
                                    y))
                                (indexed ratings)))]
    (second (nth (third tree) idx-best))))

(defn play-vs-computer [tree]
  (print-info tree)
  (cond (empty? (third tree)) (announce-winners (second tree))
        (zero? (first tree)) (recur (handle-human tree))
        :else (recur (handle-computer tree))))

(defn draw-die-svg [x y col]
  (let [calc-pt (fn [pt]
                  [(+ x (* dice-scale (first pt)))
                   (+ y (* dice-scale (second pt)))])
        f (fn [pol col]
            (polygon (map calc-pt pol) col))]
    (f [[0 -1] [-0.6 -0.75] [0 -0.5] [0.6 -0.75]]
       (brightness col 40))
    (f [[0 -0.5] [-0.6 -0.75] [-0.6 0] [0 0.25]]
       col)
    (f [[0 -0.5] [0.6 -0.75] [0.6 0] [0 0.25]]
       (brightness col -40))
    (doseq [[x y] (map list
                       [-0.05 0.125 0.3 -0.3 -0.125 0.05
                        0.2 0.2 0.45 0.45 -0.45 -0.2]
                       [-0.875 -0.8 -0.725 -0.775 -0.7 -0.625
                        -0.35 -0.05 -0.45 -0.15 -0.45 -0.05])]
      (polygon (map (fn [xx yy]
                      (calc-pt [(+ x (* xx dot-size))
                                (+ y (* yy dot-size))]))
                    [-1 -1 1 1]
                    [-1 1 1 -1])
               [255 255 255]))))

(defn draw-tile-svg [x y pos hex xx yy col chosen-tile]
  (dotimes [z 2]
    (polygon (map (fn [pt]
                    [(+ xx (* board-scale (first pt)))
                     (+ yy (* board-scale (+ (second pt)
                                             (* (- 1 z) 0.1))))])
                  [[-1 -0.2] [0 -0.5] [1 -0.2]
                   [1 0.2] [0 0.5] [-1 0.2]])
             (if (= chosen-tile pos)
               (brightness col 100)
               col)))
  (dotimes [z (second hex)]
    (draw-die-svg (+ xx (* dice-scale 0.3 (if (odd? (+ x y z))
                                            -0.3
                                            0.3)))
                  (- yy (* dice-scale z 0.8))
                  col)))

(defn make-game-link [pos]
  (str "/game.html?chosen=" pos))

(defn draw-board-svg [board chosen-tile legal-tiles]
  (doseq [y (range board-size)
          x (range board-size)]
    (let [pos (+ x (* board-size y))
          hex (board pos)
          xx (* board-scale (+ (* 2 x) (- board-size y)))
          yy (* board-scale (+ (* 0.7 y) top-offset))
          col (brightness (die-colors (first hex))
                          (* -15 (- board-size y)))]
      (if (some #{pos} legal-tiles)
        (tag g []
             (tag a ("xlink:href" (make-game-link pos))
                  (draw-tile-svg x y pos hex xx yy col chosen-tile)))
        (draw-tile-svg x y pos hex xx yy col chosen-tile)))))

(defn web-initialize []
  (reset! *cur-game-tree* (game-tree (gen-board) 0 0 true))
  (reset! *from-tile* nil))

(defn web-announce-winner [board]
  (let [ws (winners board)]
    (if (> (count ws) 1)
      (print (str "The winners are " (map player-letter ws) "! "))
      (print (str "The winner is " (player-letter (first ws)) "! ")))
    (tag a (href "game.html")
         (print "Play again."))))

(defn web-handle-human [pos]
  (let [moves (third @*cur-game-tree*)]
    (cond (nil? pos) (print "Please choose a hex to move from.")
          (= 'pass pos) (let [next (second (first moves))]
                          (reset! *cur-game-tree* next)
                          (print "Your reinforcements have been placed. ")
                          (tag a (href (make-game-link 'continue))
                               (print "Continue.")))
          (nil? @*from-tile*) (do (reset! *from-tile* pos)
                                  (print "Now choose a destination."))
          (= @*from-tile* pos) (print "Please choose a destination.")
          :else (let [selected? (fn [move] (= [@*from-tile* pos]
                                              (first move)))
                      next (second (first (filter selected? moves)))]
                  (reset! *cur-game-tree* next)
                  (reset! *from-tile* nil)
                  (print "You may now ")
                  (tag a (href (make-game-link 'pass))
                       (print "pass"))
                  (print " or make another move.")))))

(defn web-handle-computer []
  (swap! *cur-game-tree* handle-computer)
  (print "The computer has moved.")
  (let [url "game.html?computer"
        s (str "window.setTimeout('window.location=\"" url "\"', 1000)")]
    (tag script []
         (print s))))

(defn draw-dod-page [tree selected-tile]
  (svg board-width
       board-height
       (draw-board-svg (second tree)
                       selected-tile
                       (if selected-tile
                         (map (fn [move]
                                (when (= selected-tile (ffirst move))
                                  (first (rest (first move)))))
                              (third tree))
                         (map ffirst (third tree))))))

(defn dod-request-handler [path header params]
  (if (= "game.html" path)
    (do (print "<!doctype html>")
        (tag center []
             (print "Welcome to DICE OF DOOM!")
             (tag br [])
             (when (or (nil? @*cur-game-tree*) (empty? params))
               (web-initialize))
             (let [chosen (get params :chosen)
                   moves (third @*cur-game-tree*)
                   player (first @*cur-game-tree*)]
               (cond (empty? moves) (web-announce-winner
                                     (second @*cur-game-tree*))
                     (zero? player) (web-handle-human
                                     (when chosen
                                       (read-string chosen)))
                     :else (web-handle-computer))))
        (draw-dod-page @*cur-game-tree* @*from-tile*))
    (print "Sorry... I don't know that page.")))
