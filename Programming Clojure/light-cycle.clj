(ns light-cycle
  (:import (java.awt Color Dimension)
	   (java.awt.event ActionListener KeyListener)
	   (javax.swing JFrame JOptionPane JPanel Timer))
  (:use clojure.contrib.import-static))

(import-static java.awt.event.KeyEvent
	       VK_W  VK_A    VK_S    VK_D
	       VK_UP VK_LEFT VK_DOWN VK_RIGHT)

(def width 75)
(def height 50)
(def point-size 10)
(def turn-millis 75)
(def p1-dirs {VK_W [ 0 -1]
	      VK_A [-1  0]
	      VK_S [ 0  1]
	      VK_D [ 1  0]})
(def p2-dirs {VK_UP    [ 0 -1]
	      VK_LEFT  [-1  0]
	      VK_DOWN  [ 0  1]
	      VK_RIGHT [ 1  0]})
(def opposite-dir {[-1  0] [ 1  0]
		   [ 1  0] [-1  0]
		   [ 0 -1] [ 0  1]
		   [ 0  1] [ 0 -1]})

(defn add-points [& pts]
  (vec (apply map + pts)))

(defn point-to-screen-rect [[x y]]
  (map #(* point-size %) [x y 1 1]))

(defn create-bike [start-pt start-dir color]
  {:trail [start-pt]
   :dir start-dir
   :color color})

(defn create-p1 []
  (create-bike [37 50] [0 -1] (Color. 255 255 0)))

(defn create-p2 []
  (create-bike [37 0] [0 1] (Color. 0 0 255)))  

(defn move [{:keys [trail dir] :as bike}]
  (let [new-pt (add-points dir (first trail))]
    (assoc bike :trail (cons new-pt trail))))

(defn win [p1 p2]
  (cond
   (and (nil? p1) (nil? p2)) "Tie!"
   (nil? p1) "P2 wins!"
   (nil? p2) "P1 wins!"))

(defn hit-wall? [{[[x y] & _] :trail}]
  (or (neg? x)
      (neg? y)
      (> x width)
      (> y height)))

(defn hit-trails? [{[pt & _] :trail} trails]
  (some #(= pt %) trails))

(defn lose? [bike other-bike]
  (or
   (nil? bike)
   (hit-wall? bike)
   (hit-trails? bike (concat (rest (:trail bike))
			     (:trail other-bike)))))

(defn opposite-dirs? [dir1 dir2]
  (= dir1 (opposite-dir dir2)))

(defn turn [bike new-dir]
  (if (opposite-dirs? new-dir (:dir bike)) bike
      (assoc bike :dir new-dir)))

;; mutable state ahead
(defn reset-game [p1 p2]
  (dosync (ref-set p1 (create-p1))
	  (ref-set p2 (create-p2)))
  nil)

(defn update-direction [bike new-dir]
  (when new-dir
    (dosync (alter bike turn new-dir))))

(defn update-positions [p1 p2]
  (dosync
   (let [p1-lose (lose? @p1 @p2)
	 p2-lose (lose? @p2 @p1)]
     (if p1-lose (ref-set p1 nil)
	 (alter p1 move))
     (if p2-lose (ref-set p2 nil)
	 (alter p2 move))))
  nil)

;; GUI
(defn fill-point [g pt color]
  (let [[x y width height] (point-to-screen-rect pt)]
    (.setColor g color)
    (.fillRect g x y width height)))

(defn paint [g {:keys [trail color]}]
  (doseq [point trail]
    (fill-point g point color)))

(defn game-panel [frame p1 p2]
  (proxy [JPanel ActionListener KeyListener] []
    (paintComponent [g]
		    (proxy-super paintComponent g)
		    (paint g @p1)
		    (paint g @p2))
    (actionPerformed [e]
		     (update-positions p1 p2)
		     (let [winner (win @p1 @p2)]
		       (when winner
			 (reset-game p1 p2)
			 (JOptionPane/showMessageDialog frame winner)))
		     (.repaint this))
    (keyPressed [e]
		(let [p1-dir (p1-dirs (.getKeyCode e))
		      p2-dir (p2-dirs (.getKeyCode e))]
		  (cond
		   (and @p1 p1-dir) (update-direction p1 p1-dir)
		   (and @p2 p2-dir) (update-direction p2 p2-dir))))
    (getPreferredSize []
		      (Dimension. (* (inc width) point-size)
				  (* (inc height) point-size)))
    (keyReleased [e])
    (keyTyped [e])))

(defn game []
  (let [p1 (ref (create-p1))
	p2 (ref (create-p2))
	frame (JFrame. "Light Cycle")
	panel (game-panel frame p1 p2)
	timer (Timer. turn-millis panel)]
    (doto panel
      (.addKeyListener panel)
      (.setBackground (Color. 0 0 0))
      (.setFocusable true))
    (doto frame
      (.add panel)
      (.pack)
      (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
      (.setVisible true))
    (.start timer)
    [p1 p2 timer]))

(game)
