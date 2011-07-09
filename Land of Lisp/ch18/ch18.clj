(delay (+ 1 2))
(force (delay (+ 1 2)))
(defn add [a b]
  (println "I am adding now")
  (+ a b))
(def foo (delay (add 1 2)))
(force foo)

(use 'lazy)
(def foo (lazy-cons 4 7))
(lazy-car foo)
(lazy-cdr foo)
(def integers (let [f (fn f [n]
                        (lazy-cons n (f (inc n))))]
                (f 1)))
(lazy-car integers)
(lazy-car (lazy-cdr integers))
(lazy-car (lazy-cdr (lazy-cdr integers)))

(lazy-take 10 integers)
(lazy-take 10 (make-lazy '[q w e r t y u i o p a s d f]))
(lazy-take-all (make-lazy '[q w e r t y u i o p a s d f]))

(lazy-take 10 (lazy-mapcar #(java.lang.Math/sqrt %) integers))
(lazy-take 10 (lazy-mapcan (fn [x]
                             (if (even? x)
                               (make-lazy (list x))
                               (lazy-nil)))
                           integers))
(lazy-find-if odd? (make-lazy [2 4 6 7 8 10]))
(lazy-nth 4 (make-lazy '[a b c d e f g]))

(use 'doom)
(play-vs-human (game-tree [[0 1][0 3][0 1][1 2]
                           [1 3][0 3][0 3][0 1]
                           [0 3][0 3][1 1][0 2]
                           [1 3][0 3][0 1][0 3]] 0 0 true))

(play-vs-computer (game-tree [[0 1][1 2][1 1][0 3]
                              [1 3][0 1][0 3][0 3]
                              [1 3][1 2][1 2][1 2]
                              [0 3][0 3][0 2][0 2]] 0 0 true))

(play-vs-computer (game-tree [[0 2][1 2][0 1][1 2][1 2]
                              [0 1][1 2][1 3][1 3][0 3]
                              [0 1][1 2][0 3][1 1][1 2]
                              [1 1][1 3][0 2][1 2][0 1]
                              [1 3][1 1][1 1][0 3][1 3]] 0 0 true))
