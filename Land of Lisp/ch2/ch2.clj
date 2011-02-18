(use 'guess-my-number)

(def foo 5)
(def foo 6)

(defonce goo 5)
(defonce goo 6)

(quot 11 2)

(guess-my-number)

(defn return-five []
  (+ 2 3))

(bigger)

(smaller)

(smaller)

(let [a 5
      b 6]
  (+ a b))

(let [f (fn [n]
	  (+ n 10))]
  (f 5))

(let [f (fn [n]
	  (+ n 10))
      g (fn [n]
	  (- n 3))]
  (g (f 5)))

(let [a (fn [n]
	  (+ n 5))
      b (fn [n]
	  (+ (a n) 6))]
  (b 10))
