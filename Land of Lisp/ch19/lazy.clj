(ns lazy)

(defn map1 [f coll]
  (lazy-seq
   (when-let [x (first coll)]
     (cons (f x) (map1 f (rest coll))))))

(defn mapcat1 [f coll]
  (lazy-seq
   (when-let [x (first coll)]
     (concat (f x) (mapcat1 f (rest coll))))))
