(= 'fooo 'FoOo) ;; false in Clojure

(+ 1 1.0)
(defn expt [a b]
  (Math/pow a b))
(expt 53 53)
(/ 4 6)
(/ 4.0 6)

(println "Tutti Frutti")
(println "He yelled \"Stop that thief!\" from the busy street.")

(expt 2 3)
(expt 2 (+ 3 4))

'(expt 2 3)

(cons 'chicken '[cat]) ;; Clojure doesn't allow cons-ing two atoms
(cons 'chicken 'nil)
(cons 'chicken [])
(cons 'pork '[beef chicken])
(cons 'beef (cons 'chicken []))
(cons 'pork (cons 'beef (cons 'chicken [])))

(first '[pork beef chicken])
(rest '[pork beef chicken])
(rest '[pork beef chicken])
(first '[beef chicken])
(first (rest '[pork beef chicken]))
(first (rest '[pork beef chicken]))

(vector 'pork 'beef 'chicken)

'[cat [duck bat] ant]
(first '[[peas carrots tomatoes] [pork beef chicken]])
(rest '[peas carrots tomatoes])
(rest (first '[[peas carrots tomatoes] [pork beef chicken]]))
(rest (first '[[peas carrots tomatoes] [pork beef chicken]]))
(cons (cons 'peas (cons 'carrots (cons 'tomatoes [])))
      (cons (cons 'pork (cons 'beef (cons 'chicken []))) []))
(rest (rest '[[peas carrots tomatoes] [pork beef chicken] duck]))
(first (rest (rest '[[peas carrots tomatoes] [pork beef chicken] duck])))
(rest (rest (first '[[peas carrots tomatoes] [pork beef chicken] duck])))
(first (rest (first (rest '[[peas carrots tomatoes] [pork beef chicken] duck]))))
