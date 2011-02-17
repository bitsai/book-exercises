(use '[clojure.contrib.math :only (expt)])

(= 'fooo 'FoOo) ;; false in Clojure

(+ 1 1.0)

(expt 53 53)

(/ 4 6)

(/ 4.0 6)

(println "Tutti Frutti")

(println "He yelled \"Stop that thief!\" from the busy street.")

(expt 2 3)

(expt 2 (+ 3 4))

'(expt 2 3)

(cons 'chicken '(cat))

(cons 'chicken nil)

(cons 'chicken ())

(cons 'pork '(beef chicken))

(cons 'beef (cons 'chicken ()))

(cons 'pork (cons 'beef (cons 'chicken ())))

(first '(pork beef chicken))

(next '(pork beef chicken))

(next '(pork beef chicken))

(first '(beef chicken))

(first (next '(pork beef chicken)))

(fnext '(pork beef chicken))

(list 'pork 'beef 'chicken)

'(cat (duck bat) ant)

(first '((peas carrots tomatoes) (pork beef chicken)))

(next '(peas carrots tomatoes))

(next (first '((peas carrots tomatoes) (pork beef chicken))))

(nfirst '((peas carrots tomatoes) (pork beef chicken)))

(cons (cons 'peas (cons 'carrots (cons 'tomatoes ())))
      (cons (cons 'pork (cons 'beef (cons 'chicken ()))) ()))

(nnext '((peas carrots tomatoes) (pork beef chicken) duck))

(first (nnext '((peas carrots tomatoes) (pork beef chicken) duck)))

(next (nfirst '((peas carrots tomatoes) (pork beef chicken) duck)))

(fnext (fnext '((peas carrots tomatoes) (pork beef chicken) duck)))
