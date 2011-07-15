(ns server
  (:use [clojure.java.io :only (reader writer)])
  (:require [clojure.string :as str])
  (:import [java.net ServerSocket]))

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
                 [(keyword name) (decode-param value)])))))

(defn parse-url [s]
  (let [[_ url params] (re-find #"^\S+ /([^\s?]+)\?*(\S+)* \S+$" s)]
    [url (parse-params params)]))

(defn get-header-lines [rdr]
  (let [line (.readLine rdr)]
    (when (seq line)
      (cons line (get-header-lines rdr)))))

(defn get-header [rdr]
  (into {} (for [line (get-header-lines rdr)]
             (let [[name value] (str/split line #":")]
               [(keyword (str/trim name)) (str/trim value)]))))

(defn get-content-params [rdr header]
  (when-let [length (get header :content-length)]
    (let [content (char-array length)]
      (.read rdr content)
      (parse-params (apply str content)))))

(defn serve [request-handler]
  (with-open [server (ServerSocket. 8080)]
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
