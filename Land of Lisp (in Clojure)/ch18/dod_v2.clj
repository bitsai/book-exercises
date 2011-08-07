(ns dod-v2
  (:require [clojure.string :as str])
  (:use lazy))

(def num-players 2)
(def max-dice 3)
(def board-size 5)
(def board-hexnum (* board-size board-size))
(def ai-level 4)

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
  (let [ws (winners board)
        names (str/join " " (map player-letter ws))]
    (if (> (count ws) 1)
      (println (str "The winners are " names))
      (println (str "The winner is " names)))))

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
