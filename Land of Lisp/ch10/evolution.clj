(ns evolution)

(def *width* 70)
(def *height* 30)
(def *jungle* [30 10 10 10])
(def *plant-energy* 80)
(def *reproduction-energy* 200)
(def *plants* (atom #{}))
(def *animals* (atom nil))

(defn random-plant [left top width height]
  (let [pos [(+ left (rand-int width)) (+ top (rand-int height))]]
    (swap! *plants* conj pos)))

(defn add-plants []
  (apply random-plant *jungle*)
  (random-plant 0 0 *width* *height*))

(defrecord animal [x y energy dir genes])

(swap! *animals* conj (animal. (quot *width* 2) (quot *height* 2)
			       1000 0
			       (vec (repeatedly 8 #(inc (rand-int 10))))))

(defn angle [[gene & genes] n]
  (let [new-n (- n gene)]
    (if (neg? new-n)
      0
      (inc (angle genes new-n)))))

(defn turn [{:keys [dir genes] :as animal}]
  (let [n (rand-int (reduce + genes))
	new-dir (mod (+ dir (angle genes n)) 8)]
    (assoc animal :dir new-dir)))

(defn move [{:keys [x y energy dir] :as animal}]
  (let [new-x (mod (+ x (cond
			 (#{2 3 4} dir) 1
			 (#{0 6 7} dir) -1
			 :else 0))
		   *width*)
	new-y (mod (+ y (cond
			 (#{4 5 6} dir) 1
			 (#{0 1 2} dir) -1
			 :else 0))
		   *height*)]
    (assoc animal :x new-x :y new-y :energy (dec energy))))

(defn eat [{:keys [x y energy] :as animal}]
  (let [pos [x y]]
    (if (@*plants* pos)
      (do
	(swap! *plants* disj pos)
	(update-in animal [:energy] + *plant-energy*))
      animal)))

(defn reproduce [{:keys [energy genes] :as animal}]
  (if (< energy *reproduction-energy*)
    [animal]
    (let [new-e (quot energy 2)
	  i (rand-int 8)
	  new-gene (max 1 (+ (genes i) (dec (rand-int 3))))
	  new-genes (assoc genes i new-gene)]
      [(assoc animal :energy new-e)
       (assoc animal :energy new-e :genes new-genes)])))

(defn update-world []
  (reset! *animals* (remove #(<= (:energy %) 0) @*animals*))
  (reset! *animals* (reduce concat (map #(-> %
					     turn
					     move
					     eat
					     reproduce)
					@*animals*)))
  (add-plants))

(defn draw-world []
  (doseq [y (range *height*)]
    (print "|")
    (doseq [x (range *width*)]
      (print (cond
	      (some #(= [x y] [(:x %) (:y %)]) @*animals*) "X"
	      (@*plants* [x y]) "O"
	      :else " ")))
    (println "|")))

(defn evolution []
  (draw-world)
  (let [x (read)]
    (if (= x 'quit)
     nil
     (do
       (if (number? x) 
	 (doseq [i (range x)]
	   (update-world)
	   (if (zero? (mod i 1000))
	     (println ".")))
	 (update-world))
       (recur)))))

(evolution)
