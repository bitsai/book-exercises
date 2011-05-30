(ns robots
  (:use [clojure.contrib.math :only (abs)]))

(defn rand-pos []
  (rand-int 1024))

(def *inputs* {'q -65, 'w -64, 'e -63, 'a -1,
               'd   1, 'z  63, 'x  64, 'c 65})
(def *player* (atom 544))
(def *robots* (atom (repeatedly 10 rand-pos)))

(defn end-game [msg]
  (println msg)
  (System/exit 0))

(defn move-player []
  (println "qwe/asd/zxc to move, (t)eleport, (l)eave:")
  (let [c (read)
	offset (*inputs* c)]
    (cond offset (swap! *player* + offset)
          (= c 't) (reset! *player* (rand-pos))
          (= c 'l) (end-game "bye"))))

(defn manhattan-dist [x y]
  (+ (abs (- (mod x 64) (mod y 64)))
     (abs (- (bit-shift-right x 6) (bit-shift-right y 6)))))

(defn next-move [pos]
  (let [offsets (vals *inputs*)
        moves (map #(+ % pos) offsets)]
    (apply min-key #(manhattan-dist % @*player*) moves)))

(defn dead-robot? [pos]
  (> (count (filter #{pos} @*robots*)) 1))

(defn move-robot [pos]
  (if (dead-robot? pos) pos (next-move pos)))

(defn move-robots []
  (swap! *robots* #(doall (map move-robot %))))

(defn draw-pos [pos]
  (print (cond (dead-robot? pos) "#"
               (some #{pos} @*robots*) "A"
               (= pos @*player*) "@"
               :else " ")))

(defn draw-map []
  (println (apply str (repeat 66 "-")))
  (doseq [row (partition 64 (range 1024))]
    (print "|")
    (dorun (map draw-pos row))
    (println "|"))
  (println (apply str (repeat 66 "-"))))

(defn play-turn []
  (draw-map)
  (move-player)
  (move-robots)
  (cond (every? dead-robot? @*robots*) (end-game "player wins")
        (some #{@*player*} @*robots*) (end-game "player loses")
        :else (recur)))

(play-turn)
