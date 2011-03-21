(ns robots
  (:use [clojure.contrib.math :only (abs)])
  (:use [clojure.contrib.seq :only (indexed)]))

(def *directions* {'q -65, 'w -64, 'e -63, 'a -1,
		   'd   1, 'z  63, 'x  64, 'c 65})
(def *player* (atom 544))
(def *robots* (atom (vec (repeatedly 10 #(rand-int 1024)))))

(defn move-player []
  (println "qwe/asd/zxc to move, (t)eleport, (l)eave:")
  (let [c (read)
	d (*directions* c)]
    (cond
     d (swap! *player* + d)
     (= c 't) (reset! *player* (rand-int 1024))
     (= c 'l) (do (println "bye")
		  (System/exit 0)))))

(defn manhattan-distance [p1 p2]
  (+ (abs (- (mod p1 64) (mod p2 64)))
     (abs (- (bit-shift-right p1 6) (bit-shift-right p2 6)))))

(defn closest-move [rpos]
  (let [moves (map #(+ rpos %) (vals *directions*))]
    (apply min-key #(manhattan-distance @*player* %) moves)))

(defn move-robots []
  (doseq [[idx rpos] (indexed @*robots*)]
    (if (< (count (filter #{rpos} @*robots*)) 2)
      (swap! *robots* assoc idx (closest-move rpos)))))

(defn robots-dead? []
  (every? #(> % 1) (vals (frequencies @*robots*))))

(defn player-dead? []
  (some #{@*player*} @*robots*))

(defn draw-map []
  (println (apply str (repeat 66 "-")))
  (doseq [row (partition 64 (range 1024))]
    (print "|")
    (doseq [p row]
      (print (cond
	      (> (count (filter #{p} @*robots*)) 1) "#"
	      (some #{p} @*robots*) "A"
	      (= p @*player*) "@"
	      :else " ")))
    (println "|"))
  (println (apply str (repeat 66 "-"))))

(defn robots []
  (draw-map)
  (move-player)
  (move-robots)
  (cond
   (robots-dead?) (do (println "player wins")
		      (System/exit 0))
   (player-dead?) (do (println "player loses")
		      (System/exit 0))
   :else (recur)))

(robots)
