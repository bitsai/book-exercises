(ns robots)

(def rows 16)
(def cols 64)
(def inputs '{q [-1 -1] w [-1  0] e [-1  1]
              a [ 0 -1] s [ 0  0] d [ 0  1]
              z [ 1 -1] x [ 1  0] c [ 1  1]})

(def *player* (atom nil))
(def *robots* (atom nil))

(defn scrap? [pos]
  (> (count (filter #{pos} @*robots*)) 1))

(defn draw-map []
  (println (reduce str (repeat (+ cols 2) \-)))
  (doseq [row (range rows)]
    (print \|)
    (doseq [col (range cols)]
      (let [pos [row col]]
        (print (cond (some #{pos} @*robots*) (cond (= @*player* pos) \X
                                                   (scrap? pos) \#
                                                   :else \A)
                     (= @*player* pos) \@
                     :else \space))))
    (println \|))
  (println (reduce str (repeat (+ cols 2) \-))))

(defn combine [pos offset]
  (let [[x y] (map + pos offset)]
    (if (and (< -1 x rows) (< -1 y cols))
      [x y]
      pos)))

(defn rand-pos []
  [(rand-int rows) (rand-int cols)])

(defn move-player []
  (println "qwe/asd/zxc to move, (t)eleport, (l)eave:")
  (let [c (read)
        offset (inputs c)]
    (cond offset (swap! *player* combine offset)
          (= 't c) (reset! *player* (rand-pos))
          (= 'l c) (do (println "bye")
                       (System/exit 0)))))

(defn manhattan-dist [pos1 pos2]
  (reduce + (map #(Math/abs (- %1 %2)) pos1 pos2)))

(defn move-robot [pos]
  (if (scrap? pos)
    pos
    (let [offsets (vals inputs)
          moves (map #(combine pos %) offsets)]
      (apply min-key #(manhattan-dist % @*player*) moves))))

(defn move-robots []
  (reset! *robots* (doall (map move-robot @*robots*))))

(defn robots []
  (reset! *player* [(/ rows 2) (/ cols 2)])
  (reset! *robots* (repeatedly 10 rand-pos))
  (loop []
    (draw-map)
    (cond (every? scrap? @*robots*) (println "player wins")
          (some #{@*player*} @*robots*) (println "player loses")
          :else (do (move-player)
                    (move-robots)
                    (recur)))))
