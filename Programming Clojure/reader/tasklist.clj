(ns reader.tasklist
  (:gen-class
   :extends org.xml.sax.helpers.DefaultHandler
   :state state
   :init init)
  (:use [clojure.contrib.duck-streams :only (reader)])
  (:import [java.io File]
           [org.xml.sax InputSource]
	   [org.xml.sax.helpers DefaultHandler]
	   [javax.xml.parsers SAXParserFactory]))
(defn task-list [arg]
  (let [handler (new reader.tasklist)]
    (.. SAXParserFactory newInstance newSAXParser
	(parse (InputSource. (reader (File. arg)))
	       handler))
    @(.state handler)))
(defn -main [& args]
  (doseq [arg args]
    (println (task-list arg))))
(defn -init []
  [[] (atom [])])
(defn -startElement
  [this uri local qname atts]
  (when (= qname "target")
    (swap! (.state this) conj (.getValue atts "name"))))
