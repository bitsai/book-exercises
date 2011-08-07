(ns svg
  (:require [clojure.string :as str]))

(defn print-tag [name attributes closing?]
  (print \<)
  (when closing?
    (print \/))
  (print (str/lower-case name))
  (doseq [[attribute value] attributes]
    (print (format " %s=\"%s\"" (str/lower-case attribute) value)))
  (print \>))

(defn pairs [coll]
  (partition 2 coll))

(defmacro tag [name attributes & body]
  `(do (print-tag '~name
                  [~@(for [[attribute value] (pairs attributes)]
                       `['~attribute ~value])]
                  false)
       ~@body
       (print-tag '~name nil true)))

(defmacro svg [& body]
  `(tag ~'svg ["xmlns" "http://www.w3.org/2000/svg"
               "xmlns:xlink" "http://www.w3.org/1999/xlink"]
        ~@body))

(defn brightness [col amt]
  (map #(min 255 (max 0 (+ % amt))) col))

(defn svg-style [color]
  (apply format "fill:rgb(%s,%s,%s);stroke:rgb(%s,%s,%s)"
         (concat color (brightness color -100))))

(defn polygon [points color]
  (tag polygon [points (str/join (for [[x y] points] (str x "," y " ")))
                style (svg-style color)]))
