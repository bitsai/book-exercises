(delay (+ 1 2))
(force (delay (+ 1 2)))
(defn add [a b]
  (println "I am adding now")
  (+ a b))
(def foo (delay (add 1 2)))
(force foo)

;; In Clojure, can't cons two atoms
(def foo (lazy-seq (cons 4 [7])))
(first foo)
(next foo)
(def integers (let [f (fn f [n]
                        (lazy-seq (cons n (f (inc n)))))]
                (f 1)))
(first integers)
(first (next integers))
(first (next (next integers)))

(take 10 integers)
(take 10 (lazy-seq '[q w e r t y u i o p a s d f]))
(doall (lazy-seq '[q w e r t y u i o p a s d f]))

(take 10 (map #(java.lang.Math/sqrt %) integers))
(take 10 (mapcat (fn [x]
                   (if (even? x)
                     (lazy-seq (list x))
                     (lazy-seq nil)))
                 integers))
(first (filter odd? (lazy-seq [2 4 6 7 8 10])))
(nth (lazy-seq '[a b c d e f g]) 4)

(use 'doom)
(play-vs-human (game-tree [[0 1][0 3][0 1][1 2]
                           [1 3][0 3][0 3][0 1]
                           [0 3][0 3][1 1][0 2]
                           [1 3][0 3][0 1][0 3]] 0 0 true))
