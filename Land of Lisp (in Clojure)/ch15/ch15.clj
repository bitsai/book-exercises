(use 'dod-v1)
(gen-board)
(player-letter 1)
(draw-board [[0 3] [0 3] [1 3] [1 1]])

(neighbors 2)

(board-attack [[0 3] [0 3] [1 3] [1 1]] 0 1 3 3)

(add-new-dice [[0 1] [1 3] [0 2] [1 1]] 0 2)

(game-tree [[0 1] [1 1] [0 2] [1 1]] 0 0 true)

(play-vs-human (game-tree [[1 2] [1 2] [0 2] [1 1]] 0 0 true))

(play-vs-computer (game-tree [[0 3] [1 3] [0 2] [1 2]] 0 0 true))

(def foo (fn [] 5))
(foo)
(def foo (let [x 5]
           (fn [] x)))
(foo)
(let [line-number (atom 0)]
  (defn my-print [x]
    (prn @line-number)
    (prn x)
    (swap! line-number inc)
    nil))
(my-print "this")
(my-print "is")
(my-print "a")
(my-print "test")

(neighbors 0)

(defn my-length [lst]
  (if (seq lst)
    (inc (my-length (rest lst)))
    0))
(my-length '[fie foh fum])
(def big-list (range 100000))
(my-length big-list)
(defn my-length [lst]
  (let [f (fn [lst acc]
            (if (seq lst)
              (recur (rest lst) (inc acc))
              acc))]
    (f lst 0)))
(my-length '[fie foh fum])

(my-length big-list)

(play-vs-computer (game-tree [[1 1] [0 2] [0 3]
                              [0 1] [1 1] [1 2]
                              [1 2] [0 2] [1 3]] 0 0 true))
