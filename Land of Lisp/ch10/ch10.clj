(reduce + (for [i (range 5)]
	    i))

(reduce + (for [i (range 5 (inc 10))]
	    i))
(reduce + (for [i [100 20 3]]
	    i))
(doseq [i (range 5)]
  (println i))
(reduce + (for [i (range 10) :when (odd? i)]
	    i))
(doseq [i (range) :while (<= i 5)]
  (println i))
(for [i [2 3 4 5 6]]
  (* i i))
(for [x (range 10)
      y [x]]
  (+ x y))
(for [x (range 10)]
  (for [y (range 10)]
    (+ x y)))
(let [days '[monday tuesday wednesday thursday friday saturday sunday]]
  (for [pair (map list (range) days)]
    pair))

;; Clojure's immutable data don't have copy problems
(use 'evolution)
(def *parent* (atom (animal. 0 0 0 0 [1 1 1 1 1 1 1 1])))
(def *child* (atom @*parent*))
(swap! *parent* assoc-in [:genes 2] 10)
*parent*
*child*
