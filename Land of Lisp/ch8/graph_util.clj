(ns graph-util
  (:use clojure.java.io)
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
  (doseq [[name _ :as node] nodes]
    (print (dot-name name))
    (print "[label=\"")
    (print (dot-label node))
    (println "\"];")))

(defn edges->dot [edges]
  (doseq [[src paths] edges
	  [dst & content] paths]
    (print (dot-name src))
    (print "->")
    (print (dot-name dst))
    (print "[label=\"")
    (print (dot-label content))
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

(defn has-edge-to? [src dst edges]
  (let [paths (edges src)]
    (some #(= (first %) dst) paths)))

(defn uedges->dot [edges]
  (loop [[[src paths] & next-edges] (seq edges)]
    (when src
      (doseq [[dst & content] paths]
	(when-not (has-edge-to? dst src (into {} next-edges))
	  (print (dot-name src))
	  (print "--")
	  (print (dot-name dst))
	  (print "[label=\"")
	  (print (dot-label content))
	  (println "\"];")))
      (recur next-edges))))

(defn ugraph->dot [nodes edges]
   (println "graph{")
   (nodes->dot nodes)
   (uedges->dot edges)
   (println "}"))

(defn ugraph->png [fname nodes edges]
  (dot->png fname #(ugraph->dot nodes edges)))
