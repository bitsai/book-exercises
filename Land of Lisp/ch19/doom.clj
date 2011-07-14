(ns doom
  (:use [lazy]))

(def num-players 2)
(def max-dice 3)
(def board-size 5)
(def board-hexnum (* board-size board-size))
(def ai-level 4)

(defn third [coll]
  (first (next (next coll))))

(defn indexed [coll]
  (map list (range) coll))

(defn gen-board []
  (let [f (fn [] [(rand-int num-players) (inc (rand-int max-dice))])]
    (vec (repeatedly board-hexnum f))))

(defn player-letter [n]
  (char (+ 97 n)))

(defn draw-board [board]
  (doseq [row (range board-size)]
    (print (apply str (repeat (- board-size row) \space)))
    (doseq [col (range board-size)]
      (let [[player dice] (board (+ col (* board-size row)))]
        (print (str (player-letter player) "-" dice " "))))
    (newline)))

(defn neighbors [pos]
  (let [up (- pos board-size)
        down (+ pos board-size)]
    (for [p (concat [up down]
                    (when-not (zero? (mod pos board-size))
                      [(dec up) (dec pos)])
                    (when-not (zero? (mod (inc pos) board-size))
                      [(inc pos) (inc down)]))
          :when (< -1 p board-hexnum)]
      p)))

(def neighbors (memoize neighbors))

(defn board-attack [board player src dst dice]
  (assoc board
    src [player 1]
    dst [player (dec dice)]))

(defn add-new-dice [board player spare-dice]
  (letfn [(f [[[cur-player cur-dice] :as lst] n acc]
            (cond (zero? n) (into acc lst)
                  (empty? lst) acc
                  :else (if (and (= cur-player player)
                                 (< cur-dice max-dice))
                          (recur (next lst)
                                 (dec n)
                                 (conj acc [cur-player (inc cur-dice)]))
                          (recur (next lst)
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
    (lazy-cons [nil
                (game-tree (add-new-dice board player
                                         (dec spare-dice))
                           (mod (inc player) num-players)
                           0
                           true)]
               moves)))

(defn attacking-moves [board cur-player spare-dice]
  (let [player (fn [pos] (first (board pos)))
        dice (fn [pos] (second (board pos)))]
    (lazy-mapcan
     (fn [src]
       (if (= cur-player (player src))
         (lazy-mapcan
          (fn [dst]
            (if (and (not= cur-player (player dst))
                     (> (dice src) (dice dst)))
              (make-lazy
               [[[src dst]
                 (game-tree (board-attack board
                                          cur-player
                                          src
                                          dst
                                          (dice src))
                            cur-player
                            (+ spare-dice (dice dst))
                            false)]])
              (lazy-nil)))
          (make-lazy (neighbors src)))
         (lazy-nil)))
     (make-lazy (range board-hexnum)))))

(defn print-info [tree]
  (println "current player =" (player-letter (first tree)))
  (draw-board (second tree)))

(defn handle-human [tree]
  (println "choose your move:")
  (let [print-moves (fn print-moves [moves n]
                      (when-not (lazy-empty? moves)
                        (let [move (lazy-car moves)
                              action (first move)]
                          (print (str n ". "))
                          (if action
                            (println (first action) "->" (second action))
                            (println "end turn"))
                          (recur (lazy-cdr moves) (inc n)))))
        moves (third tree)]
    (print-moves moves 1)
    (second (lazy-nth (dec (read)) moves))))

(defn winners [board]
  (let [tally (map first board)
        totals (frequencies tally)
        best (apply max (vals totals))]
    (map first (filter (fn [[player total]] (= best total)) totals))))

(defn announce-winners [board]
  (let [w (winners board)]
    (if (> (count w) 1)
      (println "The game is a tie between" (map player-letter w))
      (println "The winner is" (player-letter (first w))))))

(defn play-vs-human [tree]
  (print-info tree)
  (if-not (lazy-empty? (third tree))
    (recur (handle-human tree))
    (announce-winners (second tree))))

(defn threatened? [pos board]
  (let [[player dice] (board pos)]
    (some (fn [n]
            (let [[nplayer ndice] (board n)]
              (and (not= player nplayer)
                   (> ndice dice))))
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
            (when-not (lazy-empty? moves)
              (let [x (ab-rate-position (second (lazy-car moves))
                                        player
                                        upper-limit
                                        lower-limit)]
                (if (>= x upper-limit)
                  [x]
                  (cons x (f (lazy-cdr moves) (max x lower-limit)))))))]
    (f (third tree) lower-limit)))

(defn ab-get-ratings-min [tree player upper-limit lower-limit]
  (letfn [(f [moves upper-limit]
            (when-not (lazy-empty? moves)
              (let [x (ab-rate-position (second (lazy-car moves))
                                        player
                                        upper-limit
                                        lower-limit)]
                (if (<= x lower-limit)
                  [x]
                  (cons x (f (lazy-cdr moves) (min x upper-limit)))))))]
    (f (third tree) upper-limit)))

(defn ab-rate-position [tree player upper-limit lower-limit]
  (let [moves (third tree)]
    (if-not (lazy-empty? moves)
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
   (if (zero? depth)
     (lazy-nil)
     (lazy-mapcar (fn [move]
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
    (second (lazy-nth idx-best (third tree)))))

(defn play-vs-computer [tree]
  (print-info tree)
  (cond (lazy-empty? (third tree)) (announce-winners (second tree))
        (zero? (first tree)) (recur (handle-human tree))
        :else (recur (handle-computer tree))))
