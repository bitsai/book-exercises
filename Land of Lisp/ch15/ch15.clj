(def foo (fn [] 5))
(foo)
(def foo (let [x 5]
           (fn [] x)))
(foo)
(let [line-number (atom 0)]
  (defn my-print [x]
    (println @line-number)
    (println x)
    (swap! line-number inc)
    nil))
(my-print "this")
(my-print "is")
(my-print "a")
(my-print "test")

(defn my-length [lst]
  (if lst
    (inc (my-length (next lst)))
    0))
(my-length '(fie foh fum))
(def big-list (range 100000))
(my-length big-list)
(defn my-length [lst]
  (letfn [(f [lst acc]
            (if lst
              (recur (next lst) (inc acc))
              acc))]
    (f lst 0)))
(my-length '(fie foh fum))
(my-length big-list)
