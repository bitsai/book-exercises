(delay (+ 1 2))
(force (delay (+ 1 2)))
(defn add [a b]
  (println "I am adding now")
  (+ a b))
(def foo (delay (add 1 2)))
(force foo)

;; Clojure doesn't allow cons-ing two atoms
(def foo (cons 4 [7]))
(first foo)
(rest foo)
(def integers (iterate inc 1))
(first integers)
(first (rest integers))
(first (rest (rest integers)))

(take 10 integers)
(take 10 '[q w e r t y u i o p a s d f])
(doall '[q w e r t y u i o p a s d f])

(use 'lazy)
(take 10 (map1 #(Math/sqrt %) integers))
(take 10 (mapcat1 (fn [x]
                    (when (even? x)
                      [x]))
                  integers))
(first (filter odd? [2 4 6 7 8 10]))
(nth '[a b c d e f g] 4)

(use 'dod-v2)
(play-vs-human (game-tree [[0 1] [0 3] [0 1] [1 2]
                           [1 3] [0 3] [0 3] [0 1]
                           [0 3] [0 3] [1 1] [0 2]
                           [1 3] [0 3] [0 1] [0 3]] 0 0 true))

(play-vs-computer (game-tree [[0 1] [1 2] [1 1] [0 3]
                              [1 3] [0 1] [0 3] [0 3]
                              [1 3] [1 2] [1 2] [1 2]
                              [0 3] [0 3] [0 2] [0 2]] 0 0 true))

(play-vs-computer (game-tree [[0 2] [1 2] [0 1] [1 2] [1 2]
                              [0 1] [1 2] [1 3] [1 3] [0 3]
                              [0 1] [1 2] [0 3] [1 1] [1 2]
                              [1 1] [1 3] [0 2] [1 2] [0 1]
                              [1 3] [1 1] [1 1] [0 3] [1 3]] 0 0 true))
