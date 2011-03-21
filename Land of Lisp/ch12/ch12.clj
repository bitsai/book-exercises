(print \X)

(read)

(spit "data.txt" "my data")
(slurp "data.txt")
(let [animal-noises {'dog 'woof
		     'cat 'meow}]
  (spit "animal-noises.txt" animal-noises))
(read-string (slurp "animal-noises.txt"))
(spit "data.txt" "my data")

(let [foo (with-out-str
	    (print "This will go into foo. ")
	    (print "This will also go into foo."))]
  foo)

(with-out-str
  (print "the sum of" 5 "and" 2 "is" (+ 2 5)))
