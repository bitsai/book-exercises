(use 'wumpus)
(make-edge-list)

(repeat 10 1)
(range 1 11)
(for [n (range 1 11)] (+ 100 n))

(let [a 5
      b (+ a 2)]
  b)

(defn ingredients [order]
  (mapcat (fn [burger]
	    (case burger
		  'single '[patty]
		  'double '[patty patty]
		  'double-cheese '[patty patty cheese]))
	  order))
(ingredients '(single double-cheese double))

(new-game)
