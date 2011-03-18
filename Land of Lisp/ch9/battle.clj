(ns battle
  (:use [clojure.contrib.seq :only (indexed)]))

(def *health* (atom nil))
(def *agility* (atom nil))
(def *strength* (atom nil))
(def *foes* (atom []))
(def *foe-builders* (atom []))
(def *foes-num* 12)

(defn randval [n]
  (inc (rand-int (max 1 n))))

(defn foe-dead? [f]
  (<= (:health f) 0))

;; Foes
(defmulti foe-hit (fn [[idx f] x] (class f)))
(defmethod foe-hit ::foe [[idx f] x]
  (let [name (subs (.getName (class f)) 7)
	new-f (update-in f [:health] - x)]
    (if (foe-dead? new-f)
      (println (str "You killed the " name "!"))
      (println "You hit the" name "for" x "health points!"))
    (swap! *foes* assoc idx new-f)))

(defmulti foe-show class)
(defmethod foe-show ::foe [f]
  (let [name (subs (.getName (class f)) 7)]
    (str "A fierce " name)))

(defmulti foe-attack (fn [idx f] (class f)))

;; Orc
(defrecord orc [health club-level])
(defn make-orc []
  (orc. (randval 10) (randval 8)))
(derive orc ::foe)
(swap! *foe-builders* conj make-orc)

(defmethod foe-show orc [f]
  (str "A wicked orc with a level " (:club-level f) " club"))

(defmethod foe-attack orc [_ f]
  (let [x (randval (:club-level f))]
    (println "An orc swings his club and knocks off" x "of your health.")
    (swap! *health* - x)))

;; Hydra
(defrecord hydra [health])
(defn make-hydra []
  (hydra. (randval 10)))
(derive hydra ::foe)
(swap! *foe-builders* conj make-hydra)

(defmethod foe-show hydra [f]
  (str "A malicious hydra with " (:health f) " heads"))

(defmethod foe-hit hydra [[idx f] x]
  (let [new-f (update-in f [:health] - x)]
    (if (foe-dead? new-f)
      (println "The corpse of the decapitated hydra falls to the floor!")
      (println "You knock off" x "of the hydra's heads!"))
    (swap! *foes* assoc idx new-f)))

(defmethod foe-attack hydra [idx f]
  (let [x (randval (quot (:health f) 2))
	new-f (update-in f [:health] inc)]
    (println "A hydra attacks you with" x "of its heads!")
    (println "It also grows back 1 more head!")
    (swap! *foes* assoc idx new-f)
    (swap! *health* - x)))

;; Slime
(defrecord slime [health sliminess])
(defn make-slime []
  (slime. (randval 10) (randval 5)))
(derive slime ::foe)
(swap! *foe-builders* conj make-slime)

(defmethod foe-show slime [f]
  (str "A slime with a sliminess of " (:sliminess f)))

(defmethod foe-attack slime [_ f]
  (let [x (randval (:sliminess f))]
    (println (str "A slime wraps your legs and decreases your agility by "
		  x "!"))
    (swap! *agility* - x)
    (when (zero? (rand-int 2))
      (println "It also squirts your face, taking away 1 health point!")
      (swap! *health* dec))))

;; Brigand
(defrecord brigand [health])
(defn make-brigand []
  (brigand. (randval 10)))
(derive brigand ::foe)
(swap! *foe-builders* conj make-brigand)

(defmethod foe-attack brigand [_ f]
  (let [x (max @*health* @*agility* @*strength*)]
    (cond
     (= x @*health*) (do
		       (println "A brigand hits you with his slingshot.")
		       (println "Taking off 2 health points!")
		       (swap! *health* - 2))
     (= x @*agility*) (do
			(println "A brigand whips your leg.")
			(println "Taking off 2 agility points!")
			(swap! *agility* - 2))
     :else (do
	     (println "A brigand whips your arm.")
	     (println "Taking off 2 strength points!")
	     (swap! *strength* - 2)))))

;; Foe management functions
(defn init-foes []
  (let [new-foes (repeatedly *foes-num* #((rand-nth @*foe-builders*)))]
    (reset! *foes* (vec new-foes))))

(defn foes-dead? []
  (every? foe-dead? @*foes*))

(defn show-foes []
  (println "Your foes:")
  (doseq [[idx f] (indexed @*foes*)]
    (println (inc idx) "."
	     (if (foe-dead? f)
	       "**dead**"
	       (str "(Health = " (:health f) ") " (foe-show f))))))

;; Helper functions for player attacks
(defn random-foe []
  (let [[idx f] (rand-nth (indexed @*foes*))]
    (if (foe-dead? f)
      (recur)
      [idx f])))

(defn pick-foe []
  (println "Foe #:")
  (let [x (read)]
    (if-not (and (integer? x) (>= x 1) (<= x *foes-num*))
      (do (println "That is not a valid foe number.")
	  (recur))
      (let [f (@*foes* (dec x))]
	(if (foe-dead? f)
	  (do (println "That foe is already dead.")
	      (recur))
	  [(dec x) f])))))

;; Player management functions
(defn init-player []
  (reset! *health* 30)
  (reset! *agility* 30)
  (reset! *strength* 30))

(defn player-dead? []
  (<= @*health* 0))

(defn show-player []
  (println "\nYou are a mystic monk with" @*health* "health,"
	   @*agility* "agility, and" @*strength* "strength."))

(defn player-attack []
  (println "Attack style: [k]i strike [d]ual strike [f]lurry of blows")
  (case (read)
	'k (let [x (+ 2 (randval (quot @*strength* 2)))]
	     (println "Your ki strike has a strength of" x)
	     (foe-hit (pick-foe) x))
	'd (let [x (randval (quot @*strength* 6))]
	     (println "Your double strike has a strength of" x)
	     (foe-hit (pick-foe) x)
	     (when-not (foes-dead?)
	       (foe-hit (pick-foe) x)))
	'f (dotimes [_ (inc (randval (quot @*strength* 3)))]
	     (when-not (foes-dead?)
	       (foe-hit (random-foe) 1)))
	(recur)))

;; Main game functions
(defn game-loop []
  (when-not (or (player-dead?) (foes-dead?))
    (show-player)
    (dotimes [_ (inc (quot (max 0 @*agility*) 15))]
      (when-not (foes-dead?)
	(show-foes)
	(player-attack)))
    (doseq [[idx f] (indexed @*foes*)]
      (when-not (foe-dead? f)
	(foe-attack idx f)))
    (recur)))

(defn battle []
  (init-foes)
  (init-player)
  (game-loop)
  (cond
   (player-dead?) (println "\nYou have been killed. Game over.")
   (foes-dead?) (println "\nCongratulations! You vanquished all foes.")))

(battle)
