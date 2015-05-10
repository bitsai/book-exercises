(ns day1)

(defn big [st n]
  (> (count st) n))

(defn collection-type [col]
  (cond
    (list? col)   :list
    (map? col)    :map
    (vector? col) :vector))
