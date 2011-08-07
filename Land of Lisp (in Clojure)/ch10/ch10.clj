(reduce + (range 5))

(reduce + (range 5 (inc 10)))

(reduce + [100 20 3])

(doseq [i (range 5)]
  (println i))

(reduce + (for [i (range 10) :when (odd? i)]
	    i))

(do (doseq [i (range) :while (<= i 5)]
      (println i))
    'falafel)

(for [i [2 3 4 5 6]]
  (* i i))

(let [xs (range 10)
      ys (range 10)]
  (map + xs ys))
(for [x (range 10)]
  (for [y (range 10)]
    (+ x y)))
(let [is (range)
      days '[monday tuesday wednesday thursday friday saturday sunday]]
  (map vector is days))

;; Clojure's immutable data don't have copy problems
(use 'evolution)
(def *parent* (atom (evolution.animal. 0 0 0 0 [1 1 1 1 1 1 1 1])))
(def *child* (atom @*parent*))
(swap! *parent* assoc-in [:genes 2] 10)
@*parent*
@*child*

(evolution)
