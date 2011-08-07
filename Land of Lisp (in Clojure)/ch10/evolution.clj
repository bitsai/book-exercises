(ns evolution)

(def width 70)
(def height 30)
(def jungle [30 10 10 10])
(def plant-energy 80)
(def reproduction-energy 200)

(def *plants* (atom #{}))
(def *animals* (atom []))

(defn random-plant [left top width height]
  (let [pos [(+ left (rand-int width)) (+ top (rand-int height))]]
    (swap! *plants* conj pos)))

(defn add-plants []
  (apply random-plant jungle)
  (random-plant 0 0 width height))

(defrecord animal [x y energy dir genes])

(let [genes (vec (repeatedly 8 #(inc (rand-int 10))))
      adam (animal. (quot width 2) (quot height 2) 1000 0 genes)]
  (swap! *animals* conj adam))

(defn move [animal]
  (let [{:keys [x y energy dir]} animal
        new-x (rem (+ x
                      (cond (<= 2 dir 4) 1
                            (#{1 5} dir) 0
                            :else -1)
                      width)
		   width)
	new-y (rem (+ y
                      (cond (<= 0 dir 2) -1
                            (<= 4 dir 6) 1
                            :else 0)
                      height)
		   height)]
    (assoc animal :x new-x :y new-y :energy (dec energy))))

(defn angle [genes x]
  (let [[g & gs] genes
        new-x (- x g)]
    (if (neg? new-x)
      0
      (inc (angle gs new-x)))))

(defn turn [animal]
  (let [{:keys [dir genes]} animal
        x (rand-int (reduce + genes))
	new-dir (rem (+ dir (angle genes x)) 8)]
    (assoc animal :dir new-dir)))

(defn eat [animal]
  (let [{:keys [x y energy]} animal
        pos [x y]]
    (if (@*plants* pos)
      (do (swap! *plants* disj pos)
          (update-in animal [:energy] + plant-energy))
      animal)))

(defn reproduce [animal]
  (let [{:keys [energy genes]} animal]
    (if (< energy reproduction-energy)
      [animal]
      (let [new-e (quot energy 2)
            mutation (rand-int 8)
            new-gene (max 1 (+ (genes mutation) (rand-int 3) -1))
            new-genes (assoc genes mutation new-gene)]
        [(assoc animal :energy new-e)
         (assoc animal :energy new-e :genes new-genes)]))))

(defn update-world []
  (reset! *animals* (remove #(<= (:energy %) 0) @*animals*))
  (reset! *animals* (vec (mapcat (fn [animal]
                                   (-> animal
                                       turn
                                       move
                                       eat
                                       reproduce))
                                 @*animals*)))
  (add-plants))

(defn draw-world []
  (doseq [y (range height)]
    (print "|")
    (doseq [x (range width)]
      (print (cond (some #(= [x y] [(:x %) (:y %)]) @*animals*) \M
                   (@*plants* [x y]) \*
                   :else \space)))
    (println "|")))

(defn evolution []
  (draw-world)
  (let [s (read-line)]
    (when-not (= "quit" s)
      (if-let [x (try (Integer/parseInt s) (catch Exception _))]
	(doseq [i (range x)]
	  (update-world)
	  (when (zero? (rem i 1000))
	    (println \.)))
	(update-world))
      (recur))))
