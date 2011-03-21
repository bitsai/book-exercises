(format "Add onion rings for only %.2f dollars more!" 1.5)

(println (apply str (reverse (format "Add onion rings for only %.2f dollars more!" 1.5))))

(prn "foo")
(println "foo")
(format "I am printing '%s' in the middle of this sentence." "foo")
(format "I am printing %s in the middle of this sentence." "foo")
(format "I am printing %-10s within ten spaces of room." "foo")
(format "I am printing %10s within ten spaces of room." "foo")
(format "I am printing %-12s within ten (or more) spaces of room." "foo")
(format "I am printing %s     in the middle of this sentence." "foo")
(format "The word %1$s%2$s%2$s%2$s%2$s feels very important." "foo" "!")
(format "The word %2$s%2$s%2$s%2$s%1$s feels very important." "foo" "!")

(format "The number 1000 in hexadecimal is %h" 1000)
(format "The number 1000 in binary is %s" (Integer/toString 1000 2))
(format "The number 1000 in decimal is %d" 1000)
(format "Numbers with commas in them are %,d better." 1000000)
(format "I am printing %10d within ten spaces of room" 1000000)
(format "I am printing xxx%d within ten spaces of room" 1000000)

(format "PI can be estimated as %.2f" 3.141593)
(format "PI can be estimated as %.4f" 3.141593)
(format "PI can be estimated as %.4f" Math/PI)
(format "Percentages are %.1f percent better than fractions" (* 0.77 100))
(format "I wish I had %.2f dollars in my bank account." 1000000.2)

(do (print 22)
    (newline)
    (print 33))
(do (print 22)
    (newline)
    (newline)
    (print 33))
(do (print (format "this is on one line\n"))
    (print (format "\nthis is on another line")))
(print (format "this wil print \n\n\n\n\non two lines spread far apart"))

(defn random-animal []
  (rand-nth ["dog" "tick" "tiger" "walrus" "kangaroo"]))
(random-animal)
(dotimes [_ 10]
  (println (format "%-10s%-10s%-10s"
		   (random-animal)
		   (random-animal)
		   (random-animal))))
(dotimes [_ 10]
  (println (format "%s" (random-animal))))
(dotimes [_ 10]
  (println (format "%-10s%-10s%-10s"
		   (random-animal)
		   (random-animal)
		   (random-animal))))

(def *animals* (repeatedly 10 random-animal))
*animals*
(apply println (map #(format "I see a %s!" %) *animals*))
(apply print (map
	      (fn [[x y]] (format "I see a %s... or was it a %s?\n" x y))
	      (partition 2 *animals*)))

(doseq [row (partition 10 (range 100))]
  (print "|")
  (doseq [n row] (print (format "%2d " n)))
  (println "|"))
