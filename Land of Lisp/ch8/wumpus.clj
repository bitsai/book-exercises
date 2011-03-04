(ns wumpus
  (:use graph-util))

(def *city-edges* (atom nil))
(def *city-nodes* (atom nil))
(def *player-pos* (atom nil))
(def *visited-nodes* (atom nil))
(def *node-num* 30)
(def *edge-num* 45)
(def *worm-num* 3)
(def *cop-odds* 15)

(defn random-node []
  (inc (rand-int *node-num*)))

(defn edge-pair [a b]
  (if (not= a b)
    [[a b] [b a]]))

(defn make-edge-list []
  (apply concat (repeatedly *edge-num*
			    #(edge-pair (random-node) (random-node)))))

(defn direct-edges [node edge-list]
  (filter #(= node (first %)) edge-list))

(defn get-connected [node edge-list]
  (loop [[n & ns] [node]
	 visited #{}]
    (if-not n
      visited
      (let [neighbors (distinct (map second (direct-edges n edge-list)))]
	(recur (concat ns (remove visited neighbors))
	       (conj visited n))))))

(defn find-islands [nodes edge-list]
  (loop [[n & _ :as cur-nodes] nodes
	 islands []]
    (if-not n
      islands
      (let [connected (get-connected n edge-list)
	    unconnected (remove connected cur-nodes)]
	(recur unconnected
	       (conj islands connected))))))

(defn make-bridges [[island & islands]]
  (if islands
    (concat (edge-pair (first island) (ffirst islands))
	    (make-bridges islands))))

(defn connect-all-islands [nodes edge-list]
  (concat (make-bridges (find-islands nodes edge-list)) edge-list))

(defn edges-to-map [edge-list]
  (into {} (for [[n1 _] edge-list]
	     [n1 (distinct (map second (direct-edges n1 edge-list)))])))

(defn add-cops [edge-map edges-with-cops]
  (into {} (for [[n1 n-edges] edge-map]
	     [n1 (for [n2 n-edges]
		   (if (some (set edges-with-cops) (edge-pair n1 n2))
		     [n2 'cops]
		     [n2]))])))

(defn make-city-edges []
  (let [nodes (range 1 (inc *node-num*))
	edge-list (connect-all-islands nodes (make-edge-list))
	cops (filter (fn [_] (zero? (rand-int *cop-odds*))) edge-list)]
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
	worms (repeatedly *worm-num* #(random-node))]
    (into {} (for [n (range 1 (inc *node-num*))]
	       [n (concat
		   (cond
		    (= n wumpus) ['wumpus]
		    (within-two? n wumpus edge-map) ['blood!])
		   (cond
		    (some #{n} worms) ['glow-worm]
		    (some #(within-one? n % edge-map) worms) ['lights!])
		   (cond
		    (some #(> (count %) 1) (edge-map n)) ['sirens!]))]))))

(defn find-empty-node []
  (let [x (random-node)]
    (if (zero? (count (@*city-nodes* x)))
      x
      (recur))))

(defn draw-city []
  (ugraph->png "city.dot" @*city-nodes* @*city-edges*))

(defn new-game []
  (reset! *city-edges* (make-city-edges))
  (reset! *city-nodes* (make-city-nodes @*city-edges*))
  (reset! *player-pos* (find-empty-node))
  (reset! *visited-nodes* [@*player-pos*])
  (draw-city))
