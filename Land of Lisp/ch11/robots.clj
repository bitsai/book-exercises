(ns robots)

(def *rows* 16)
(def *cols* 64)

(def *inputs* {'q [-1 -1] 'w [-1  0] 'e [-1  1]
               'a [ 0 -1] 's [ 0  0] 'd [ 0  1]
               'z [ 1 -1] 'x [ 1  0] 'c [ 1  1]})

(def *player* (atom nil))
(def *robots* (atom nil))

(defn combine [pos offset]
  (let [[x y] (map + pos offset)]
    (if (and (< -1 x *rows*) (< -1 y *cols*))
      [x y]
      pos)))

(defn rand-pos []
  [(rand-int *rows*) (rand-int *cols*)])

(defn move-player [input]
  (let [offset (*inputs* input)]
    (cond offset (swap! *player* combine offset)
          (= input 't) (reset! *player* (rand-pos)))))

(defn scrap? [pos]
  (> (count (filter #{pos} @*robots*)) 1))

(defn manhattan-dist [pos1 pos2]
  (apply + (map #(Math/abs (- %1 %2)) pos1 pos2)))

(defn best-move [pos]
  (let [offsets (vals *inputs*)
        moves (map #(combine pos %) offsets)]
    (apply min-key #(manhattan-dist % @*player*) moves)))

(defn move-robot [pos]
  (if (scrap? pos) pos (best-move pos)))

(defn move-robots []
  (reset! *robots* (doall (map move-robot @*robots*))))

(defn index [[x y]]
  (+ (* (inc x) (+ *cols* 3)) y 1))

(defn draw-map []
  (let [row (str "|" (apply str (repeat *cols* " ")) "|\n")
        output1 (vec (str (apply str (repeat (+ *cols* 2) "-")) "\n"
                          (apply str (repeat *rows* row))
                          (apply str (repeat (+ *cols* 2) "-"))))
        output2 (assoc output1 (index @*player*) \O)
        output3 (reduce (fn [output r]
                          (cond
                           (= r @*player*) (assoc output (index r) \X)
                           (scrap? r) (assoc output (index r) \S)
                           :else (assoc output (index r) \R)))
                        output2
                        @*robots*)]
    (println (apply str output3))))

(defn end-game [msg]
  (println msg)
  (System/exit 0))

(defn check-end-game []
  (cond (every? scrap? @*robots*) (end-game "Player wins!")
        (some #{@*player*} @*robots*) (end-game "Player loses!")
        :else (println "qwe/asd/zxc to move, t to teleport")))

(defn new-game []
  (reset! *player* [(/ *rows* 2) (/ *cols* 2)])
  (reset! *robots* (repeatedly 10 rand-pos))
  (draw-map)
  (check-end-game))

(defn process-input [input]
  (move-player input)
  (move-robots)
  (draw-map)
  (check-end-game))

(new-game)

(loop []
  (process-input (read))
  (recur))
