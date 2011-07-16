(ns graph-util
  (:use [clojure.java.io :only (writer)])
  (:require [clojure.string :as str]))

(def max-label-length 30)

(defn dot-name [exp]
  (str/replace (print-str exp) #"[^\w]" "_"))

(defn dot-label [exp]
  (if exp
    (let [s (print-str exp)]
      (if (> (count s) max-label-length)
	(str (subs s 0 (- max-label-length 3)) "...")
	s))
    ""))

(defn nodes->dot [nodes]
  (doseq [node nodes]
    (print (dot-name (first node)))
    (print "[label=\"")
    (print (dot-label node))
    (println "\"];")))

(defn edges->dot [edges]
  (doseq [[src src-edges] edges
	  [dst & exp] src-edges]
    (print (dot-name src))
    (print "->")
    (print (dot-name dst))
    (print "[label=\"")
    (print (dot-label exp))
    (println "\"];")))

(defn graph->dot [nodes edges]
  (println "digraph{")
  (nodes->dot nodes)
  (edges->dot edges)
  (println "}"))

(defn dot->png [fname thunk]
  (binding [*out* (writer fname)]
    (thunk)
    (.close *out*))
  (.exec (Runtime/getRuntime) (str "dot -Tpng -O " fname)))

(defn graph->png [fname nodes edges]
  (dot->png fname #(graph->dot nodes edges)))

(defn maplist [f coll]
  (when (seq coll)
    (f coll)
    (recur f (rest coll))))

(defn uedges->dot [edges]
  (maplist (fn [cur-edges]
             (let [[src src-edges] (first cur-edges)]
               (doseq [[dst & exp] src-edges]
                 (when-not (some #(= dst (first %)) (rest edges))
                   (print (dot-name src))
                   (print "--")
                   (print (dot-name dst))
                   (print "[label=\"")
                   (print (dot-label exp))
                   (println "\"];")))))
           (seq edges)))

(defn ugraph->dot [nodes edges]
  (println "graph{")
  (nodes->dot nodes)
  (uedges->dot edges)
  (println "}"))

(defn ugraph->png [fname nodes edges]
  (dot->png fname #(ugraph->dot nodes edges)))
