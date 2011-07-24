(ns orc-battle
  (:require [clojure.string :as str]))

(def *player-health* (atom nil))
(def *player-agility* (atom nil))
(def *player-strength* (atom nil))

(def *monsters* (atom nil))
(def *monster-builders* (atom nil))
(def monster-num 12)

(defn indexed [coll]
  (map list (range) coll))

(defn randval [n]
  (inc (rand-int (max 1 n))))

(defn type-of [m]
  (let [strs (str/split (str (type m)) #"\.")]
    (last strs)))

(defn monster-dead? [m]
  (<= (:health m) 0))

;; The Generic Monster
(defmulti monster-hit (fn [[idx m] x] (type m)))
(defmethod monster-hit ::Monster [[idx m] x]
  (let [new-m (update-in m [:health] - x)]
    (if (monster-dead? new-m)
      (println (str "You killed the " (type-of m) "!"))
      (println "You hit the" (type-of m) "for" x "health!"))
    (swap! *monsters* assoc idx new-m)))

(defmulti monster-show type)
(defmethod monster-show ::Monster [m]
  (str "A fierce " (type-of m)))

(defmulti monster-attack (fn [idx m] (type m)))

;; The Wicked Orc
(defrecord orc [health club-level])
(derive orc ::Monster)
(swap! *monster-builders* conj #(orc. (randval 10) (randval 8)))

(defmethod monster-show orc [m]
  (str "A wicked orc with a level " (:club-level m) " club"))

(defmethod monster-attack orc [idx m]
  (let [x (randval (:club-level m))]
    (println "An orc swings his club at you for" x "health!")
    (swap! *player-health* - x)))

;; The Malicious Hydra
(defrecord hydra [health])
(derive hydra ::Monster)
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

;; The Slimey Slime Mold
(defrecord slime [health sliminess])
(derive slime ::Monster)
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

;; The Cunning Brigand
(defrecord brigand [health])
(derive brigand ::Monster)
(swap! *monster-builders* conj #(brigand. (randval 10)))

(defmethod monster-attack brigand [idx m]
  (let [x (max @*player-health* @*player-agility* @*player-strength*)]
    (cond (= x @*player-health*)
          (do (println "A brigand hits with his slingshot for 2 health!")
              (swap! *player-health* - 2))
          (= x @*player-agility*)
          (do (println "A brigand whips your leg for 2 agility!")
              (swap! *player-agility* - 2))
          :else
          (do (println "A brigand whips your arm for 2 strength!")
              (swap! *player-strength* - 2)))))

;; Monster management functions
(defn init-monsters []
  (let [build-monster #((rand-nth @*monster-builders*))
        new-monsters (vec (repeatedly monster-num build-monster))]
    (reset! *monsters* new-monsters)))

(defn monsters-dead? []
  (every? monster-dead? @*monsters*))

(defn show-monsters []
  (println "Your foes:")
  (doseq [[idx m] (indexed @*monsters*)]
    (println (str (inc idx) ".")
             (if (monster-dead? m)
               "**dead**"
               (str "(Health " (:health m) ") " (monster-show m))))))

;; Helper functions for player attacks
(defn random-monster []
  (let [[idx m] (rand-nth (indexed @*monsters*))]
    (if (monster-dead? m)
      (recur)
      [idx m])))

(defn pick-monster []
  (println "Monster #:")
  (let [x (read)]
    (if-not (and (integer? x) (<= 1 x monster-num))
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
  (println (str "Valiant Knight: "
                "(Health " @*player-health* ") "
                "(Agility " @*player-agility* ") "
                "(Strength " @*player-strength* ")")))

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
      (newline))
    (doseq [[idx m] (indexed @*monsters*)]
      (when-not (monster-dead? m)
        (monster-attack idx m)))
    (newline)
    (recur)))

(defn orc-battle []
  (init-monsters)
  (init-player)
  (game-loop)
  (when (player-dead?)
    (println "You have been killed. Game over."))
  (when (monsters-dead?)
    (println "Congratulations! You have vanquished all foes.")))
