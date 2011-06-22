(ns doom)

(def *num-players* 2)
(def *max-dice* 3)
(def *board-size* 2)
(def *board-hexnum* (* *board-size* *board-size*))

(defn gen-board []
  (let [f (fn [] [(rand-int *num-players*) (inc (rand-int *max-dice*))])]
    (vec (repeatedly *board-hexnum* f))))

(defn player-letter [n]
  (char (+ 97 n)))

(defn draw-board [board]
  (doseq [row (range *board-size*)]
    (print (apply str (repeat (- *board-size* row) \space)))
    (doseq [col (range *board-size*)]
      (let [[player dice] (board (+ col (* *board-size* row)))]
        (print (str (player-letter player) "-" dice " "))))
    (newline)))

(defn neighbors [pos]
  (let [up (- pos *board-size*)
        down (+ pos *board-size*)]
    (for [p (concat [up down]
                    (when-not (zero? (mod pos *board-size*))
                      [(dec up) (dec pos)])
                    (when-not (zero? (mod (inc pos) *board-size*))
                      [(inc pos) (inc down)]))
          :when (< -1 p *board-hexnum*)]
      p)))

(defn board-attack [board player src dst dice]
  (assoc board
    src [player 1]
    dst [player (dec dice)]))

(defn add-new-dice [board player spare-dice]
  (letfn [(f [[[cur-player cur-dice] :as lst] n]
            (cond (empty? lst) nil
                  (zero? n) lst
                  :else (if (and (= cur-player player)
                                 (< cur-dice *max-dice*))
                          (cons [cur-player (inc cur-dice)]
                                (f (rest lst) (dec n)))
                          (cons [cur-player cur-dice]
                                (f (rest lst) n)))))]
    (vec (f board spare-dice))))

(declare game-tree add-passing-move attacking-moves)

(defn game-tree [board player spare-dice first-move?]
  [player
   board
   (add-passing-move board
                     player
                     spare-dice
                     first-move?
                     (attacking-moves board player spare-dice))])

(defn add-passing-move [board player spare-dice first-move? moves]
  (if first-move?
    moves
    (cons [nil
           (game-tree (add-new-dice board player (dec spare-dice))
                      (mod (inc player) *num-players*)
                      0
                      true)]
          moves)))

(defn attacking-moves [board cur-player spare-dice]
  (let [player (fn [pos] (first (board pos)))
        dice (fn [pos] (second (board pos)))]
    (mapcat (fn [src]
              (when (= (player src) cur-player)
                (mapcat (fn [dst]
                          (when (and (not= (player dst) cur-player)
                                     (> (dice src) (dice dst)))
                            [[[src dst]
                              (game-tree (board-attack board
                                                       cur-player
                                                       src
                                                       dst
                                                       (dice src))
                                         cur-player
                                         (+ spare-dice (dice dst))
                                         nil)]]))
                        (neighbors src))))
            (range *board-hexnum*))))

(defn print-info [tree]
  (println "current player =" (player-letter (first tree)))
  (draw-board (second tree)))

(defn handle-human [tree]
  (println "choose your move:")
  (let [moves (second (rest tree))]
    (doseq [[n [action]] (map list (range) moves)]
      (print (str (inc n) ". "))
      (if action
        (println (first action) "->" (second action))
        (println "end turn")))
    (second (nth moves (dec (read))))))

(defn winners [board]
  (let [tally (map first board)
        totals (frequencies tally)
        best (apply max (vals totals))]
    (map first (filter (fn [[player total]] (= total best)) totals))))

(defn announce-winners [board]
  (let [w (winners board)]
    (if (> (count w) 1)
      (println "The game is a tie between" (map player-letter w))
      (println "The winner is" (player-letter (first w))))))

(defn play-vs-human [tree]
  (print-info tree)
  (if (seq (second (rest tree)))
    (recur (handle-human tree))
    (announce-winners (second tree))))

(play-vs-human (game-tree [[1 2][1 2][0 2][1 1]] 0 0 true))
