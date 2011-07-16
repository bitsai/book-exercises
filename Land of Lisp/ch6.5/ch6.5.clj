(defn half [n]
  (/ n 2))
half
(fn [n] (/ n 2))
#(/ % 2) ;; Clojure shorthand for lambdas
(map (fn [n] (/ n 2)) [2 4 6])
(map #(/ % 2) [2 4 6])
