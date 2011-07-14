(ns dod-web
  (:use [doom])
  (:use [server])
  (:use [svg]))

(def board-width 900)
(def board-height 500)
(def board-scale 64)
(def top-offset 3)
(def dice-scale 40)
(def dot-size 0.05)
(def die-colors [[255 63 63] [63 63 255]])

(defn draw-die-svg [x y col]
  (let [calc-pt (fn [pt]
                  [(+ x (* dice-scale (first pt)))
                   (+ y (* dice-scale (second pt)))])
        f (fn [pol col]
            (polygon (map calc-pt pol) col))]
    (f [[0 -1] [-0.6 -0.75] [0 -0.5] [0.6 -0.75]]
       (brightness col 40))
    (f [[0 -0.5] [-0.6 -0.75] [-0.6 0] [0 0.25]]
       col)
    (f [[0 -0.5] [0.6 -0.75] [0.6 0] [0 0.25]]
       (brightness col -40))
    (doseq [[x y] (map list
                       [-0.05 0.125 0.3 -0.3 -0.125 0.05
                        0.2 0.2 0.45 0.45 -0.45 -0.2]
                       [-0.875 -0.8 -0.725 -0.775 -0.7 -0.625
                        -0.35 -0.05 -0.45 -0.15 -0.45 -0.05])]
      (polygon (map (fn [xx yy]
                      (calc-pt [(+ x (* xx dot-size))
                                (+ y (* yy dot-size))]))
                    [-1 -1 1 1]
                    [-1 1 1 -1])
               [255 255 255]))))

(defn draw-tile-svg [x y pos hex xx yy col chosen-tile]
  (dotimes [z 2]
    (polygon (map (fn [pt]
                    [(+ xx (* board-scale (first pt)))
                     (+ yy (* board-scale (+ (second pt)
                                             (* (- 1 z) 0.1))))])
                  [[-1 -0.2] [0 -0.5] [1 -0.2]
                   [1 0.2] [0 0.5] [-1 0.2]])
             (if (= chosen-tile pos)
               (brightness col 100)
               col)))
  (dotimes [z (second hex)]
    (draw-die-svg (+ xx
                     (* dice-scale
                        0.3
                        (if (odd? (+ x y z))
                          -0.3
                          0.3)))
                  (- yy (* dice-scale z 0.8))
                  col)))

(defn make-game-link [pos]
  (str "/game.html?chosen=" pos))

(defn draw-board-svg [board chosen-tile legal-tiles]
  (doseq [y (range board-size)
          x (range board-size)]
    (let [pos (+ x (* board-size y))
          hex (board pos)
          xx (* board-scale (+ (* 2 x) (- board-size y)))
          yy (* board-scale (+ (* 0.7 y) top-offset))
          col (brightness (die-colors (first hex))
                          (* -15 (- board-size y)))]
      (if (some #{pos} legal-tiles)
        (tag g []
             (tag a ("xlink:href" (make-game-link pos))
                  (draw-tile-svg x y pos hex xx yy col chosen-tile)))
        (draw-tile-svg x y pos hex xx yy col chosen-tile)))))
