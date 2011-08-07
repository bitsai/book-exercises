;; Clojure vectors
[nil nil nil]
(def x (atom [nil nil nil]))
(nth @x 1)
(def x (atom [nil nil nil]))
(swap! x assoc 1 'foo)
@x
(nth @x 1)

;; Clojure lists can't be 'modified' at will
(def foo (atom '[a b c]))
(second @foo)
(swap! foo assoc 1 'z)
@foo
(reset! foo [nil nil nil nil])
(swap! foo assoc 2 '[x y z])
@foo
(swap! foo assoc-in [2 0] {})
(swap! foo assoc-in [2 0 'zoink] 5)
@foo

(nth '[foo bar baz] 1)

{}
(def x (atom {}))
(@x 'yup)
(def x (atom {}))
(swap! x assoc 'yup 25)
(@x 'yup)
(def *drink-order* (atom {}))
(swap! *drink-order* assoc 'bill 'double-espresso)
(swap! *drink-order* assoc 'lisa 'small-drip-coffee)
(swap! *drink-order* assoc 'john 'medium-latte)
(@*drink-order* 'lisa)

(Math/round 2.4)
(defn foo []
  [3 7])
(foo)
(+ (first (foo)) 5)
(let [[a b] (foo)]
  (* a b))

(def edge-num 1000)
(def node-num 1000)
(defn random-node []
  (inc (rand-int node-num)))
(defn edge-pair [a b]
  (if (not= a b)
    [[a b] [b a]]))
(defn make-edge-list []
  (reduce into (repeatedly edge-num
                           #(edge-pair (random-node) (random-node)))))
(defn direct-edges [node edge-list]
  (filter #(= node (first %)) edge-list))
(defn get-connected [node edge-list]
  (loop [[n & ns] [node]
	 connected #{}]
    (if (nil? n)
      connected
      (let [neighbors (distinct (map second (direct-edges n edge-list)))]
	(recur (into ns (remove connected neighbors))
	       (conj connected n))))))
(time (dotimes [_ 100] (get-connected 1 (make-edge-list))))
(defn edges-to-map [edge-list]
  (reduce (fn [tab [a b]]
            (update-in tab [a] conj b))
          {}
          edge-list))
(defn get-connected-hash [node edge-map]
  (loop [[n & ns] [node]
	 connected #{}]
    (if (nil? n)
      connected
      (let [neighbors (edge-map n)]
	(recur (into ns (remove connected neighbors))
	       (conj connected n))))))
(time (dotimes [_ 100]
	(get-connected-hash 1 (edges-to-map (make-edge-list)))))

(defrecord person [name age waist-size favorite-color])
(def *bob* (atom (person. "Bob" 35 32 "blue")))
@*bob*
(:age @*bob*)
(swap! *bob* assoc :age 36)
;; Clojure records don't have print/read symmetry
(def that-guy {:name "Bob", :age 35, :waist-size 32, :favorite-color "blue"})
(:age that-guy)

(defn make-person [name age waist-size favorite-color]
  (list name age waist-size favorite-color))
(defn person-age [person]
  (second person))
(def bob (make-person "bob" 35 32 "blue"))
bob
(person-age bob)

(count '[a b c])
(count "blub")
(count [nil nil nil nil nil])

(first (filter number? '[a b 5 d]))
(count (filter #{\s} "mississippi"))
(count (take-while #(not (= \4 %)) "2kewl4skewl"))
(some number? '[a b 5 d])
(every? number? '[a b 5 d])

(reduce + [3 4 6 5 2])
(reduce (fn [best item]
	  (if (and (even? item) (> item best))
	    item
	    best))
	0
	[7 4 6 5 2])
(reduce (fn [best item]
	  (if (and (even? item) (> item best))
	    item
	    best))
	[7 4 6 5 2])
(defn sum [lst]
  (reduce + lst))
(sum [1 2 3])
(sum [1 2 3 4 5])
(sum "blablabla")
(map (fn [x]
       (if (= x \s)
         \S
         x))
     "this is a string")

(subs "america" 2 6)
(sort < [5 8 2 4 9 3 6])

(number? 5)
(defn add [a b]
  (cond (and (number? a) (number? b)) (+ a b)
        (and (sequential? a) (sequential? b)) (concat a b)))
(add 3 4)
(add '[a b] '[c d])
(defmulti add (fn [a b] [(type a) (type b)]))
(defmethod add [Number Number] [a b]
  (+ a b))
(defmethod add [clojure.lang.Seqable clojure.lang.Seqable] [a b]
  (concat a b))
(add 3 4)
(add '[a b] '[c d])

(dotimes [i 3]
  (println (str i ". Hatchoo!")))

(dotimes [_ 10]
  (print (rand-int 5) ""))

(use 'orc-battle)
(defrecord monster [health])
(defn make-monster []
  (monster. (randval 10)))
(make-monster)
(make-monster)
(make-monster)
(type-of 'foo)
(type-of 5)
(type-of "foo")
(type-of (make-monster))

(orc-battle)
