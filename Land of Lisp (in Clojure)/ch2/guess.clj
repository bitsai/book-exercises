(ns guess)

(def *small* (atom 1))
(def *big* (atom 100))

(defn guess-my-number []
  (quot (+ @*small* @*big*) 2))

(defn smaller []
  (reset! *big* (dec (guess-my-number)))
  (guess-my-number))

(defn bigger []
  (reset! *small* (inc (guess-my-number)))
  (guess-my-number))

(defn start-over []
  (reset! *small* 1)
  (reset! *big* 100)
  (guess-my-number))
