(Math/sin 0.5)

(defn add-widget [database widget]
  (cons widget database))
(def *database* (atom nil))
(defn main-loop []
  (println "Please enter the name of a new widget:")
  (swap! *database* add-widget (read))
  (println "The database contains the following: " @*database*)
  (recur))
(main-loop)

(def *my-list* (atom [4 7 2 3]))

(doseq [n (range (count @*my-list*))]
  (swap! *my-list* assoc n (+ (nth @*my-list* n) 2)))
@*my-list*

(defn add-two [lst]
  (when (seq lst)
    (cons (+ 2 (first lst)) (add-two (rest lst)))))
(add-two [4 7 2 3])

(map #(+ % 2) [4 7 2 3])
