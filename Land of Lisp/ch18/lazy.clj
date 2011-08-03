(ns lazy)

(defn map1
  "Like map, but returns an unchunked sequence."
  [f coll]
  (lazy-seq
   (when-let [x (first coll)]
     (cons (f x) (map1 f (rest coll))))))

(defn mapcat1
  "Like mapcat, but returns an unchunked sequence."
  [f coll]
  (lazy-seq
   (when-let [x (first coll)]
     (concat (f x) (mapcat1 f (rest coll))))))
