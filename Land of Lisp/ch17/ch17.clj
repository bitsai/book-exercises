(use 'svg)
(print-tag 'mytag '[[color blue] [height 9]] false)

(tag mytag [color 'blue height (+ 4 5)])
(macroexpand '(tag mytag [color 'blue height (+ 4 5)]))
(tag mytag [color 'blue size 'big]
     (tag first_inner_tag [])
     (tag second_inner_tag []))

(tag html []
     (tag body []
          (print "Hello World!")))
(defmacro html [& body]
  `(tag ~'html []
        ~@body))
(defmacro body [& body]
  `(tag ~'body []
        ~@body))
(html
 (body
  (print "Hello World!")))

(brightness [255 0 0] -100)
(defn circle [center radius color]
  (tag circle [cx (first center)
               cy (second center)
               r radius
               style (svg-style color)]))
(svg (circle [50 50] 50 [255 0 0])
     (circle [100 100] 50 [0 0 255]))

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

(use 'wizard-v3)
(look)
(game-repl)

(weld 'chain 'bucket)
(game-repl)
(game-repl)

(game-repl)
