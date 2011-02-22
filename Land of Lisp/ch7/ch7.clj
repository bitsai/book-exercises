(use 'graph-util)
(use 'clojure.java.io)

(cons 1 (cons 2 (cons 3 nil)))

(cons 1 (cons 2 3)) ;; Doesn't work in Clojure, which doesn't have dotted lists

;; Pairs, the Clojure way
'(2 3)
[2 3]

(def foo (cycle '(1 2 3))) ;; infinite list, the Clojure way

(def drink-order (atom {'bill 'double-espresso
			'lisa 'small-drip-coffee
			'john 'medium-latte}))

(@drink-order 'lisa)

(swap! drink-order assoc 'lisa 'large-mocha-with-whipped-cream)

(@drink-order 'lisa)

(def house '((walls (mortar (cement)
			    (water)
			    (sand))
		    (bricks))
	     (windows (glass)
		      (frame)
		      (curtains))
	     (roof (shingles)
		   (chimney))))

(def wizard-nodes {'living-room '(you are in the living-room. a wizard is snoring loudly on the couth.)
		   'garden '(you are in a beautiful garden. there is a well in front of you.)
		   'attic '(you are in the attic. there is a giant welding torch in the corner.)})

(def wizard-edges {'living-room '((garden west door)
				  (attic upstairs ladder))
		   'garden '((living-room east door))
		   'attic '((living-room downstairs ladder))})

(defn substitute-if [replacement pred coll]
  (loop [[x & xs] coll
	 acc []]
    (cond
     (nil? x) acc
     (pred x) (recur xs (conj acc replacement))
     :else (recur xs (conj acc x)))))

(apply str (substitute-if \e #(Character/isDigit %) "I'm a l33t hack3r!"))

(substitute-if 0 odd? '(1 2 3 4 5 6 7 8))

(nodes->dot wizard-nodes)

(edges->dot wizard-edges)

(graph->dot wizard-nodes wizard-edges)

(binding [*out* (writer "testfile.txt")]
  (println "Hello File!")
  (.close *out*))

(let [cigar 5]
  cigar)

:cigar

;; Clojure does not allow binding to keywords
(let [:cigar 5]
  :cigar)

(graph->png "wizard.dot" wizard-nodes wizard-edges)

(defn maplist [f coll]
  (for [i (range (count coll))]
    (f (drop i coll))))

(dorun (maplist println '(a b c)))

(ugraph->png "uwizard.dot" wizard-nodes wizard-edges)
