(ns lazy)

;; Inspired by Joy of Clojure's seq1
(defn- map-join [join f colls]
  (lazy-seq
   (when (every? seq colls)
     (join (apply f (map first colls))
           (map-join join f (map rest colls))))))

(defn map1
  "Like map, but returns a de-chunked/1-at-a-time lazy sequence."
  [f & colls]
  (map-join cons f colls))

(defn mapcat1
  "Like mapcat, but returns a de-chunked/1-at-a-time lazy sequence."
  [f & colls]
  (map-join concat f colls))
