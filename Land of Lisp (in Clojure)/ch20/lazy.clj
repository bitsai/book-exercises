(ns lazy)

;; From The Joy of Clojure
(defn seq1
  "Like seq, but returns an unchunked sequence."
  [coll]
  (lazy-seq
   (when-let [[x] (seq coll)]
     (cons x (seq1 (rest coll))))))

(defn map1
  "Like map, but applies f one-at-a-time.  Returns an unchunked sequence."
  [f & colls]
  (apply map f (map seq1 colls)))

(defn join
  "Like concat, but works on coll of colls.  Returns an unchunked sequence."
  [colls]
  (lazy-seq
   (when-let [[x] (seq colls)]
     (concat x (join (rest colls))))))

(defn mapcat1
  "Like mapcat, but applies f one-at-a-time.  Returns an unchunked sequence."
  [f & colls]
  (join (apply map1 f colls)))
