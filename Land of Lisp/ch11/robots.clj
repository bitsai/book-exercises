(ns robots
  (:use [clojure.contrib.math :only (abs)]))

(def *rows* 16)
(def *cols* 64)

(def *inputs* {'q [-1 -1] 'w [-1  0] 'e [-1  1]
               'a [ 0 -1] 's [ 0  0] 'd [ 0  1]
               'z [ 1 -1] 'x [ 1  0] 'c [ 1  1]})

(def *player* (atom nil))
(def *robots* (atom nil))

(defn rand-pos []
  [(rand-int *rows*) (rand-int *cols*)])

(defn combine [pos offset]
  (let [[x y] (map + pos offset)]
    (if (and (< -1 x *rows*) (< -1 y *cols*))
      [x y]
      pos)))

(defn move-player [input]
  (let [offset (*inputs* input)]
    (cond offset (swap! *player* combine offset)
          (= input 't) (reset! *player* (rand-pos)))))

(defn manhattan-dist [pos1 pos2]
  (apply + (map #(abs (- %1 %2)) pos1 pos2)))

(defn best-move [pos]
  (let [offsets (vals *inputs*)
        moves (map #(combine pos %) offsets)]
    (apply min-key #(manhattan-dist % @*player*) moves)))

(defn scrap? [pos]
  (> (count (filter #{pos} @*robots*)) 1))

(defn move-robot [pos]
  (if (scrap? pos) pos (best-move pos)))

(defn move-robots []
  (swap! *robots* #(doall (map move-robot %))))

(defn draw-pos [pos]
  (cond (and (= pos @*player*) (some #{pos} @*robots*)) "X"
        (= pos @*player*) "O"
        (scrap? pos) "S"
        (some #{pos} @*robots*) "R"
        :else " "))

(defn draw-row [row]
  (let [draw-col (fn [col] (draw-pos [row col]))]
    (str "|" (apply str (map draw-col (range *cols*))) "|\n")))

(defn draw-map []
  (println (str (apply str (repeat (+ *cols* 2) "-")) "\n"
                (apply str (map draw-row (range *rows*)))
                (apply str (repeat (+ *cols* 2) "-")))))

(defn new-game []
  (reset! *player* [(/ *rows* 2) (/ *cols* 2)])
  (reset! *robots* (repeatedly 10 rand-pos))
  (draw-map)
  (println "qwe/asd/zxc to move, t to teleport"))

(defn end-game [msg]
  (println msg)
  (System/exit 0))

(defn process-input [input]
  (move-player input)
  (move-robots)
  (draw-map)
  (cond (every? scrap? @*robots*) (end-game "Player wins!")
        (some #{@*player*} @*robots*) (end-game "Player loses!")
        :else (println "qwe/asd/zxc to move, t to teleport")))

(new-game)

(loop []
  (process-input (read))
  (recur))
