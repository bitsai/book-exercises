(def small (atom 1))
(def big (atom 100))

(def foo 5)
(def foo 6)

(defonce goo 5)
(defonce goo 6)

(defn guess-my-number []
  (quot (+ @small @big) 2))

(quot 11 2)

(defn return-five []
  (+ 2 3))

(defn smaller []
  (reset! big (dec (guess-my-number)))
  (guess-my-number))

(defn bigger []
  (reset! small (inc (guess-my-number)))
  (guess-my-number))

(defn start-over []
  (reset! small 1)
  (reset! big 100)
  (guess-my-number))

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
