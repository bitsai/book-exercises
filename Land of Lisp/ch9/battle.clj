(ns battle)

(def *player-health* (atom nil))
(def *player-agility* (atom nil))
(def *player-strength* (atom nil))

(def *monsters* (atom nil))
(def *monster-builders* (atom nil))
(def *monster-num* 12)

(defn indexed [coll]
  (map list (range) coll))

(defn randval [n]
  (inc (rand-int (max 1 n))))

(defn monster-dead? [m]
  (<= (:health m) 0))

(defn type-name [m]
  (subs (.getName (type m)) (inc (count (str *ns*)))))

;; Monsters
(defmulti monster-hit (fn [[idx m] x] (class m)))
(defmethod monster-hit ::monster [[idx m] x]
  (let [new-m (update-in m [:health] - x)]
    (if (monster-dead? new-m)
      (println (str "You killed the " (type-name m) "!"))
      (println "You hit the" (type-name m) "for" x "health!"))
    (swap! *monsters* assoc idx new-m)))

(defmulti monster-show class)
(defmethod monster-show ::monster [m]
  (str "A fierce " (type-name m)))

(defmulti monster-attack (fn [idx m] (class m)))

;; Orc
(defrecord orc [health club-level])
(derive orc ::monster)
(swap! *monster-builders* conj #(orc. (randval 10) (randval 8)))

(defmethod monster-show orc [m]
  (str "A wicked orc with a level " (:club-level m) " club"))

(defmethod monster-attack orc [idx m]
  (let [x (randval (:club-level m))]
    (println "An orc swings his club at you for" x "health!")
    (swap! *player-health* - x)))

;; Hydra
(defrecord hydra [health])
(derive hydra ::monster)
(swap! *monster-builders* conj #(hydra. (randval 10)))

(defmethod monster-show hydra [m]
  (str "A malicious hydra with " (:health m) " heads"))

(defmethod monster-hit hydra [[idx m] x]
  (let [new-m (update-in m [:health] - x)]
    (if (monster-dead? new-m)
      (println "The fully decapitated hydra falls to the floor!")
      (println "You lop off" x "of the hydra's heads!"))
    (swap! *monsters* assoc idx new-m)))

(defmethod monster-attack hydra [idx m]
  (let [x (randval (quot (:health m) 2))
	new-m (update-in m [:health] inc)]
    (println "A hydra attacks you with" x "of its heads!")
    (swap! *player-health* - x)
    (println "It also grows back 1 more head!")
    (swap! *monsters* assoc idx new-m)))

;; Slime
(defrecord slime [health sliminess])
(derive slime ::monster)
(swap! *monster-builders* conj #(slime. (randval 10) (randval 5)))

(defmethod monster-show slime [m]
  (str "A slime with a sliminess of " (:sliminess m)))

(defmethod monster-attack slime [idx m]
  (let [x (randval (:sliminess m))]
    (println "A slime wraps around your legs for" x "agility!")
    (swap! *player-agility* - x)
    (when (zero? (rand-int 2))
      (println "It also squirts in your face for 1 health!")
      (swap! *player-health* dec))))

;; Brigand
(defrecord brigand [health])
(derive brigand ::monster)
(swap! *monster-builders* conj #(brigand. (randval 10)))

(defmethod monster-attack brigand [idx m]
  (let [x (max @*player-health* @*player-agility* @*player-strength*)]
    (cond (= x @*player-health*)
          (do
            (println "A brigand hits with his slingshot for 2 health!")
            (swap! *player-health* - 2))
          (= x @*player-agility*)
          (do
            (println "A brigand whips your leg for 2 agility!")
            (swap! *player-agility* - 2))
          :else
          (do
            (println "A brigand whips your arm for 2 strength!")
            (swap! *player-strength* - 2)))))

;; Monster management functions
(defn init-monsters []
  (let [make-random-monster #((rand-nth @*monster-builders*))
        new-monsters (vec (repeatedly *monster-num* make-random-monster))]
    (reset! *monsters* new-monsters)))

(defn monsters-dead? []
  (every? monster-dead? @*monsters*))

(defn show-monsters []
  (println "Your foes:")
  (doseq [[idx m] (indexed @*monsters*)]
    (print (str (inc idx) ". "))
    (if (monster-dead? m)
      (println "**dead**")
      (println (str "(Health = " (:health m) ")") (monster-show m)))))

;; Helper functions for player attacks
(defn random-monster []
  (let [[idx m] (rand-nth (indexed @*monsters*))]
    (if (monster-dead? m)
      (recur)
      [idx m])))

(defn pick-monster []
  (println "Monster #:")
  (let [x (read)]
    (if-not (and (integer? x) (<= 1 x *monster-num*))
      (do (println "That is not a valid monster number.")
	  (recur))
      (let [m (nth @*monsters* (dec x))]
	(if (monster-dead? m)
	  (do (println "That monster is already dead.")
	      (recur))
	  [(dec x) m])))))

;; Player management functions
(defn init-player []
  (reset! *player-health* 30)
  (reset! *player-agility* 30)
  (reset! *player-strength* 30))

(defn player-dead? []
  (<= @*player-health* 0))

(defn show-player []
  (println "[Valiant Knight]"
           @*player-health* "health,"
	   @*player-agility* "agility,"
           @*player-strength* "strength"))

(defn player-attack []
  (println "Attack style: [s]tab [d]ouble swing [r]oundhouse")
  (case (read)
    's (let [x (+ 2 (randval (quot @*player-strength* 2)))]
         (println (str "Your stab has a strength of " x "."))
         (monster-hit (pick-monster) x))
    'd (let [x (randval (quot @*player-strength* 6))]
         (println (str "Your double swing has a strength of " x "."))
         (monster-hit (pick-monster) x)
         (when-not (monsters-dead?)
           (monster-hit (pick-monster) x)))
    'r (dotimes [_ (inc (randval (quot @*player-strength* 3)))]
         (when-not (monsters-dead?)
           (monster-hit (random-monster) 1)))
    (do (println "That is not a valid attack.")
        (recur))))

;; Main game functions
(defn game-loop []
  (when-not (or (player-dead?) (monsters-dead?))
    (show-player)
    (dotimes [_ (inc (quot (max 0 @*player-agility*) 15))]
      (when-not (monsters-dead?)
	(show-monsters)
	(player-attack)))
    (when-not (monsters-dead?)
      (newline)
      (doseq [[idx m] (indexed @*monsters*)]
        (when-not (monster-dead? m)
          (monster-attack idx m))))
    (newline)
    (recur)))

(defn battle []
  (init-monsters)
  (init-player)
  (game-loop)
  (cond (player-dead?)
        (println "You have been killed. Game over.")
        (monsters-dead?)
        (println "Congratulations! You vanquished all foes.")))

(battle)
