(require '[clojure.string :as str])
(defn print-tag [name attributes closing?]
  (print \<)
  (when closing?
    (print \/))
  (print (str/lower-case name))
  (doseq [[attribute value] attributes]
    (print (format " %s=\"%s\"" (str/lower-case attribute) value)))
  (print \>))
(print-tag 'mytag '[[color blue] [height 9]] false)

(defn pairs [coll]
  (partition 2 coll))
(defmacro tag [name attributes & body]
  `(do (print-tag '~name
                  (vector ~@(for [[attribute value] (pairs attributes)]
                              `(vector '~attribute ~value)))
                  false)
       ~@body
       (print-tag '~name nil true)))
(tag mytag [color 'blue height (+ 4 5)])
(macroexpand '(tag mytag [color 'blue height (+ 4 5)]))
(tag mytag [color 'blue size 'big]
     (tag first_inner_tag [])
     (tag second_inner_tag []))

(tag html []
     (tag body []
          (print "Hello World!")))
(defmacro html [& body]
  `(tag "html" []
        ~@body))
(defmacro body [& body]
  `(tag "body" []
        ~@body))
(html
 (body
  (print "Hello World!")))

(defmacro svg [& body]
  `(tag "svg" ["xmlns" "http://www.w3.org/2000/svg"
               "xmlns:xlink" "http://www.w3.org/1999/xlink"]
        ~@body))
(defn brightness [col amt]
  (map #(min 255 (max 0 (+ % amt))) col))
(brightness [255 0 0] -100)
(defn svg-style [color]
  (apply format "fill:rgb(%s,%s,%s);stroke:rgb(%s,%s,%s)"
         (into color (brightness color -100))))
(defn circle [center radius color]
  (tag circle [cx (first center)
               cy (second center)
               r radius
               style (svg-style color)]))
(svg (circle [50 50] 50 [255 0 0])
     (circle [100 100] 50 [0 0 255]))

(defn polygon [points color]
  (tag polygon [points (str/join (for [[x y] points] (str x "," y " ")))
                style (svg-style color)]))
(defn random-walk [value length]
  (when-not (zero? length)
    (cons value
          (random-walk (if (zero? (rand-int 2))
                         (dec value)
                         (inc value))
                       (dec length)))))
(random-walk 100 10)
(let [output (with-out-str
               (svg (dotimes [_ 10]
                      (polygon (concat
                                [[0 200]]
                                (map vector (range) (random-walk 100 400))
                                [[400 200]])
                               (repeatedly 3 #(rand-int 256))))))]
  (spit "random_walk.svg" output))
