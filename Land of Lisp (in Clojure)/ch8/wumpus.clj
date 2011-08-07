(ns wumpus
  (:use graph-util))

(def node-num 30)
(def edge-num 45)
(def worm-num 3)
(def cop-odds 15)

(def *city-nodes* (atom nil))
(def *city-edges* (atom nil))
(def *visited-nodes* (atom nil))
(def *player-pos* (atom nil))

(defn random-node []
  (inc (rand-int node-num)))

(defn edge-pair [a b]
  (if (not= a b)
    [[a b] [b a]]))

(defn make-edge-list []
  (reduce into (repeatedly edge-num
                           #(edge-pair (random-node) (random-node)))))

(defn direct-edges [node edge-list]
  (filter #(= node (first %)) edge-list))

(defn get-connected [node edge-list]
  (loop [[n & ns] [node]
	 connected #{}]
    (if (nil? n)
      connected
      (let [neighbors (distinct (map second (direct-edges n edge-list)))]
	(recur (into ns (remove connected neighbors))
	       (conj connected n))))))

(defn find-islands [nodes edge-list]
  (loop [ns nodes
	 islands []]
    (if (empty? ns)
      islands
      (let [connected (get-connected (first ns) edge-list)]
	(recur (remove connected ns)
	       (conj islands connected))))))

(defn connect-islands [islands]
  (when (> (count islands) 1)
    (concat (edge-pair (ffirst islands) (ffirst (rest islands)))
	    (connect-islands (rest islands)))))

(defn connect-all-islands [nodes edge-list]
  (concat (connect-islands (find-islands nodes edge-list)) edge-list))

(defn edges-to-map [edge-list]
  (reduce (fn [tab [a b]]
            (update-in tab [a] conj b))
          {}
          edge-list))

(defn add-cops [edge-map edges-with-cops]
  (let [cops (set edges-with-cops)]
    (into {} (for [[node1 neighbors] edge-map]
               [node1 (for [node2 neighbors]
                        (if (some cops (edge-pair node1 node2))
                          [node2 'cops]
                          [node2]))]))))

(defn make-city-edges []
  (let [nodes (range 1 (inc node-num))
	edge-list (connect-all-islands nodes (make-edge-list))
	cops (filter (fn [_] (zero? (rand-int cop-odds))) edge-list)]
    (add-cops (edges-to-map edge-list) cops)))

(defn neighbors [node edge-map]
  (map first (edge-map node)))

(defn within-one? [a b edge-map]
  (some #{b} (neighbors a edge-map)))

(defn within-two? [a b edge-map]
  (or (within-one? a b edge-map)
      (some #(within-one? % b edge-map) (neighbors a edge-map))))

(defn make-city-nodes [edge-map]
  (let [wumpus (random-node)
	worms (repeatedly worm-num #(random-node))]
    (into {} (for [n (range 1 (inc node-num))]
	       [n (vec (concat
                        (cond (= n wumpus)
                              '[wumpus]
                              (within-two? n wumpus edge-map)
                              '[blood!])
                        (cond (some #{n} worms)
                              '[glow-worm]
                              (some #(within-one? n % edge-map) worms)
                              '[lights!])
                        (cond (some #(= 'cops (second %)) (edge-map n))
                              '[sirens!])))]))))

(defn find-empty-node []
  (let [x (random-node)]
    (if (zero? (count (@*city-nodes* x)))
      x
      (recur))))

(defn draw-city []
  (ugraph->png "city.dot" @*city-nodes* @*city-edges*))

(defn known-city-nodes []
  (let [visible (distinct (concat @*visited-nodes*
                                  (mapcat #(neighbors % @*city-edges*)
                                          @*visited-nodes*)))]
    (into {} (for [node visible]
	       [node (if (@*visited-nodes* node)
                       (let [description (@*city-nodes* node)]
                         (if (= @*player-pos* node)
                           (conj description '*)
                           description))
                       '[?])]))))

(defn known-city-edges []
  (into {} (for [node1 @*visited-nodes*]
	     [node1 (for [[node2 :as x] (@*city-edges* node1)]
                      (if (@*visited-nodes* node2)
                        x
                        [node2]))])))

(defn draw-known-city []
  (ugraph->png "known-city.dot" (known-city-nodes) (known-city-edges)))

(defn handle-new-place [edge pos charging?]
  (let [description (@*city-nodes* pos)
	has-worm? (and (some #{'glow-worm} description)
		       (not (@*visited-nodes* pos)))]
    (swap! *visited-nodes* conj pos)
    (reset! *player-pos* pos)
    (draw-known-city)
    (cond (some #{'cops} edge) (println "You ran into cops. Game over.")
          (some #{'wumpus} description) (if charging?
                                          (println "You killed Wumpus!")
                                          (println "Wumpus killed you!"))
          charging? (println "You wasted your last bullet. Game over.")
          has-worm? (let [new-pos (random-node)]
                      (println "Ran into Glow Worm Gang! Now at" new-pos)
                      (handle-new-place nil new-pos false)))))

(defn handle-direction [pos charging?]
  (let [edges (@*city-edges* @*player-pos*)
        edge (first (filter #(= pos (first %)) edges))]
    (if edge
      (handle-new-place edge pos charging?)
      (println "That location can't be reached from here!"))))

(defn walk [pos]
  (handle-direction pos false))

(defn charge [pos]
  (handle-direction pos true))

(defn new-game []
  (reset! *city-edges* (make-city-edges))
  (reset! *city-nodes* (make-city-nodes @*city-edges*))
  (reset! *player-pos* (find-empty-node))
  (reset! *visited-nodes* #{@*player-pos*})
  (draw-city)
  (draw-known-city))
