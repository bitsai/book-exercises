(use 'wizard-v1)
(nodes 'garden)
(describe-location 'living-room nodes)

(describe-path '[garden west door])

(describe-paths 'living-room edges)

(edges 'living-room)

(map describe-path '[[garden west door] [attic upstairs ladder]])
(map #(Math/sqrt %) [1 2 3 4 5])
(map first '[[foo bar] [baz qux]])
(map #(first %) '[[foo bar] [baz qux]])
;; does not work in Clojure, which is a Lisp-1
(let [first "Honda Civic"]
  (map first '[[foo bar] [baz qux]]))

(concat '[mary had] '[a] '[little lamb])
(reduce into '[[mary had] [a] [little lamb]])
(reduce into '[[THERE IS A DOOR GOING WEST FROM HERE.]
               [THERE IS A LADDER GOING UPSTAIRS FROM HERE.]])

(objects-at 'living-room objects @*object-locations*)

(describe-objects 'living-room objects @*object-locations*)

(look)

(find* 'y '[[5 x] [3 y] [7 z]] second)
(walk 'west)

(def *foo* (atom [1 2 3]))
(swap! *foo* #(cons 7 %))
@*foo*
(walk 'east)
(pickup 'whiskey)

(inventory)
