(ns server
  (:use [clojure.java.io])
  (:require [clojure.string :as str]))

(defn http-char
  ([cs]
     (http-char cs \space))
  ([[_ c1 c2] default]
     (try
       (str (char (Integer/parseInt (str c1 c2) 16)))
       (catch Exception _ default))))

(defn decode-param [s]
  (when s
    (-> s
        (str/replace #"%.." http-char)
        (str/replace \+ \space))))

(defn parse-params [s]
  (when s
    (into {} (for [pair (str/split s #"&")]
               (let [[name value] (str/split pair #"=")]
                 [(str/upper-case name) (decode-param value)])))))

(defn parse-url [s]
  (let [[_ url params] (re-find #"^\S+ /([^\s?]+)\?*(\S+)* \S+$" s)]
    [url (parse-params params)]))

(defn get-header-lines [rdr]
  (let [line (.readLine rdr)]
    (if (seq line)
      (cons line (get-header-lines rdr)))))

(defn get-header [rdr]
  (into {} (for [line (get-header-lines rdr)]
             (let [[name value] (str/split line #":")]
               [(str/upper-case (str/trim name)) (str/trim value)]))))

(defn get-content-params [rdr header]
  (if-let [length (header "CONTENT-LENGTH")]
    (let [content (char-array length)]
      (.read rdr content)
      (parse-params (apply str content)))))

(defn serve [request-handler]
  (with-open [server (java.net.ServerSocket. 8080)]
    (loop []
      (with-open [socket (.accept server)
                  rdr (reader socket)
                  wrtr (writer socket)]
        (let [[path path-params] (parse-url (.readLine rdr))
              header (get-header rdr)
              params (merge path-params (get-content-params rdr header))]
          (binding [*out* wrtr]
            (request-handler path header params))))
      (recur))))

(defn hello-request-handler [path header params]
  (if (= path "greeting")
    (if-let [name (get params "NAME")]
      (println (format "<html>Nice to meet you, %s!</html>" name))
      (println "<html><form>Name?<input name='name'/></form></html>"))
    (println "Sorry... I don't know that page.")))

(serve hello-request-handler)
