(defn mystery [input]
  (if input data-1 data-2))
(def a '(1 2))
(def b (cons 0 a))

(defn stack-consuming-fibo [n]
  (cond
   (= n 0) 0
   (= n 1) 1
   :else (+ (stack-consuming-fibo (- n 1))
	    (stack-consuming-fibo (- n 2)))))
(stack-consuming-fibo 9)
(stack-consuming-fibo 1000000)
(defn tail-fibo [n]
  (letfn [(fib [current next n]
	       (if (zero? n) current
		   (fib next (+ current next) (dec n))))]
    (fib 0 1 n)))
(tail-fibo 9)
(tail-fibo 1000000)
(defn recur-fibo [n]
  (letfn [(fib [current next n]
	       (if (zero? n) current
		   (recur next (+ current next) (dec n))))]
    (fib 0 1 n)))
(recur-fibo 9)
(recur-fibo 1000000)
(defn lazy-seq-fibo
  ([] (concat [0 1] (lazy-seq-fibo 0 1)))
  ([a b]
     (let [n (+ a b)]
       (lazy-seq
	(cons n (lazy-seq-fibo b n))))))
(take 10 (lazy-seq-fibo))
(rem (nth (lazy-seq-fibo) 1000000) 1000)
(take 5 (iterate (fn [[a b]] [b (+ a b)]) [0 1]))
(defn fibo []
  (map first (iterate (fn [[a b]] [b (+ a b)]) [0 1])))
(def lots-o-fibs (take 1000000000 (fibo)))
(nth lots-o-fibs 100)
(doc take)
(take 1000000000 (fibo))
(set! *print-length* 10)
(take 1000000000 (fibo))
(fibo)
(def head-fibo (lazy-cat [0 1] (map + head-fibo (rest head-fibo))))
(take 10 head-fibo)
(nth head-fibo 1000000)

[:h :t :t :h :h :h]
(defn count-heads-pairs [coll]
  (loop [cnt 0
	 coll coll]
    (if (empty? coll) cnt
	(recur (if (= :h (first coll) (second coll)) (inc cnt)
		   cnt)
	       (rest coll)))))
(count-heads-pairs [:h :h :h :t :h])
(count-heads-pairs [:h :t :h :t :h])
(defn by-pairs [coll]
  (let [take-pair (fn [c]
		    (when (next c) (take 2 c)))]
    (lazy-seq
     (when-let [pair (seq (take-pair coll))]
       (cons pair (by-pairs (rest coll)))))))
(by-pairs [:h :t :t :h :h :h])
(defn count-heads-pairs [coll]
  (count (filter (fn [pair] (every? #(= :h %) pair))
		 (by-pairs coll))))
(partition 2 [:h :t :t :h :h :h])
(partition 2 1 [:h :t :t :h :h :h])
(by-pairs [:h :t :t :h :h :h])
(use '[clojure.contrib.def :only (defvar)])
(defvar count-if (comp count filter) "Count items matching a filter")
(def count-if (comp count filter))
(count-if odd? [1 2 3 4 5])
(defn count-runs
  "Count runs of length n where pred is true in coll."
  [n pred coll]
  (count-if #(every? pred %) (partition n 1 coll)))
(count-runs 2 #(= % :h) [:h :t :t :h :h :h])
(count-runs 2 #(= % :t) [:h :t :t :h :h :h])
(count-runs 3 #(= % :h) [:h :t :t :h :h :h])
(defvar count-heads-pairs (partial count-runs 2 #(= % :h))
  "Count runs of length two that are both heads.")
(def count-heads-pairs (partial count-runs 2 #(= % :h)))
(partial count-runs 1 #(= % :h))
(fn [coll] (count-runs 1 #(= % :h) coll))
(defn faux-curry [& args] (apply partial partial args))
(def add-3 (partial + 3))
(add-3 7)
(def add-3 ((faux-curry + ) 3))
(add-3 7)
((faux-curry true?) (= 1 1))
((curry true?) (= 1 1))
(((faux-curry true?) (= 1 1)))

(declare my-odd? my-even?)
(defn my-odd? [n]
  (if (= n 0) false
      (my-even? (dec n))))
(defn my-even? [n]
  (if (= n 0) true
      (my-odd? (dec n))))
(map my-even? (range 10))
(map my-odd? (range 10))
(my-even? (* 1000 1000 1000))
(defn even? [n] (zero? (bit-and n 1)))
(defn odd? [n] (not (even? n)))
(defn parity [n]
  (loop [n n
	 par 0]
    (if (= n 0) par
	(recur (dec n) (- 1 par)))))
(map parity (range 10))
(defn my-even? [n] (= 0 (parity n)))
(defn my-odd? [n] (= 1 (parity n)))
(trampoline list)
(trampoline + 1 2)
(defn trampoline-fibo [n]
  (let [fib (fn fib [f-2 f-1 current]
	      (let [f (+ f-2 f-1)]
		(if (= n current) f
		    #(fib f-1 f (inc current)))))]
    (cond
     (= n 0) 0
     (= n 1) 1
     :else (fib 0 1 2))))
(trampoline trampoline-fibo 9)
(rem (trampoline trampoline-fibo 1000000) 1000)
(declare my-odd? my-even?)
(defn my-odd? [n]
  (if (= n 0) false
      #(my-even? (dec n))))
(defn my-even? [n]
  (if (= n 0) true
      #(my-odd? (dec n))))
(trampoline my-even? 1000000)
(declare replace-sym replace-sym-expr)
(defn replace-sym [coll oldsym newsym]
  (if (empty? coll) ()
      (cons (replace-sym-expr (first coll) oldsym newsym)
	    (replace-sym (rest coll) oldsym newsym))))
(defn replace-sym-expr [sym-expr oldsym newsym]
  (if (symbol? sym-expr) (if (= sym-expr oldsym) newsym sym-expr)
      (replace-sym sym-expr oldsym newsym)))
(defn deeply-nested [n]
  (loop [n n
	 result '(bottom)]
    (if (= n 0) result
	(recur (dec n) (list result)))))
(deeply-nested 5)
(deeply-nested 25)
(set! *print-level* 25)
(deeply-nested 5)
(deeply-nested 25)
(replace-sym (deeply-nested 5) 'bottom 'deepest)
(replace-sym (deeply-nested 10000) 'bottom 'deepest)
(defn- coll-or-scalar [x & _] (if (coll? x) :collection :scalar))
(defmulti replace-sym coll-or-scalar)
(defmethod replace-sym :collection [coll oldsym newsym]
  (lazy-seq
   (when (seq coll)
     (cons (replace-sym (first coll) oldsym newsym)
	   (replace-sym (rest coll) oldsym newsym)))))
(defmethod replace-sym :scalar [obj oldsym newsym]
  (if (= obj oldsym) newsym obj))
(replace-sym (deeply-nested 10000) 'bottom 'deepest)
(declare m f)
(defn m [n]
  (if (zero? n) 0
      (- n (f (m (dec n))))))
(defn f [n]
  (if (zero? n) 1
      (- n (m (f (dec n))))))
(time (m 250))
(def m (memoize m))
(def f (memoize f))
(time (m 250))
(time (m 250))
(m 10000)
(def m-seq (map m (iterate inc 0)))
(def f-seq (map f (iterate inc 0)))
(nth m-seq 250)
(time (nth m-seq 10000))
