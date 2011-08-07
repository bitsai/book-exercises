(cons 1 (cons 2 (cons 3 nil)))

(cons 1 (cons 2 3)) ;; Clojure doesn't have dotted lists
[1 2 3]

[2 3] ;; Pairs, the Clojure way

(def foo (cycle [1 2 3])) ;; Circular lists, the Clojure way

(def *drink-order* (atom '{bill double-espresso
                           lisa small-drip-coffee
                           john medium-latte}))
(@*drink-order* 'lisa)
(swap! *drink-order* assoc 'lisa 'large-mocha-with-whipped-cream)
(@*drink-order* 'lisa)

(def house '((walls (mortar (cement)
			    (water)
			    (sand))
		    (bricks))
	     (windows (glass)
		      (frame)
		      (curtains))
	     (roof (shingles)
		   (chimney))))

(def wizard-nodes '{living-room [you are in the living-room. a wizard is
                                 snoring loudly on the couth.]
                    garden [you are in a beautiful garden. there is a
                            well in front of you.]
                    attic [you are in the attic. there is a giant welding
                           torch in the corner.]})
(def wizard-edges '{living-room [[garden west door]
                                 [attic upstairs ladder]]
                    garden [[living-room east door]]
                    attic [[living-room downstairs ladder]]})

(use 'graph-util)
(dot-name 'living-room)
(dot-name 'foo!)
(dot-name '24)
(defn substitute-if [substitute pred xs]
  (for [x xs]
    (if (pred x)
      substitute
      x)))
(let [digit-char? (fn [c] (Character/isDigit c))]
  (reduce str (substitute-if \e digit-char? "I'm a l33t hack3r!")))
(substitute-if 0 odd? [1 2 3 4 5 6 7 8])

(nodes->dot wizard-nodes)

(edges->dot wizard-edges)

(graph->dot wizard-nodes wizard-edges)

(let [s (with-out-str (println "Hello File!"))]
  (spit "testfile.txt" s))

(let [cigar 5]
  cigar)
:cigar
(let [:cigar 5]
  :cigar)

(graph->png "wizard.dot" wizard-nodes wizard-edges)

(dorun (map println '(a b c)))
(maplist println '(a b c))
(ugraph->png "uwizard.dot" wizard-nodes wizard-edges)
