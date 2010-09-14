(ns tron
  (:import (java.awt Color Dimension)
	   (java.awt.event ActionListener KeyListener)
	   (javax.swing JPanel JFrame Timer JOptionPane))
  (:use clojure.contrib.import-static))

(import-static java.awt.event.KeyEvent VK_LEFT VK_RIGHT VK_UP VK_DOWN)

(def width 75)
(def height 50)
(def point-size 10)
(def turn-millis 75)
(def win-length 5)
(def dirs {VK_LEFT  [-1  0]
	   VK_RIGHT [ 1  0]
	   VK_UP    [ 0 -1]
	   VK_DOWN  [ 0  1]})
(def opposite-dir {[-1  0] [ 1  0]
		   [ 1  0] [-1  0]
		   [ 0 -1] [ 0  1]
		   [ 0  1] [ 0 -1]})

(defn add-points [& pts] (vec (apply map + pts)))

(defn point-to-screen-rect [[x y]] (map #(* point-size %) [x y 1 1]))

(defn create-bike []
  {:body [[1 1]]
   :dir [1 0]
   :color (Color. 255 255 0)})

(defn move [{:keys [body dir] :as bike}]
  (let [new-point (add-points (first body) dir)
	new-body (cons new-point body)]
    (assoc bike :body new-body)))

(defn win? [bike] false)

(defn head-overlaps-body? [{[head & body] :body}] (some #(= head %) body))

(defn lose? [bike] (head-overlaps-body? bike))

(defn opposite-dirs? [dir1 dir2] (= dir1 (opposite-dir dir2)))

(defn turn [bike newdir]
  (if (opposite-dirs? newdir (:dir bike)) bike
      (assoc bike :dir newdir)))

;; mutable state ahead
(defn reset-game [bike]
  (dosync (ref-set bike (create-bike)))
  nil)

(defn update-direction [bike newdir]
  (when newdir
    (dosync (alter bike turn newdir))))

(defn update-positions [bike]
  (dosync (alter bike move))
  nil)

;; GUI
(defn fill-point [g pt color]
  (let [[x y width height] (point-to-screen-rect pt)]
    (.setColor g color)
    (.fillRect g x y width height)))

(defn paint [g {:keys [body color]}]
  (doseq [point body]
    (fill-point g point color)))

(defn game-panel [frame bike]
  (proxy [JPanel ActionListener KeyListener] []
    (paintComponent [g]
		    (proxy-super paintComponent g)
		    (paint g @bike))
    (actionPerformed [e]
		     (update-positions bike)
		     (when (lose? @bike)
		       (reset-game bike)
		       (JOptionPane/showMessageDialog frame "You lose!"))
		     (when (win? @bike)
		       (reset-game bike)
		       (JOptionPane/showMessageDialog frame "You win!"))
		     (.repaint this))
    (keyPressed [e]
		(update-direction bike (dirs (.getKeyCode e))))
    (getPreferredSize []
		      (Dimension. (* (inc width) point-size)
				  (* (inc height) point-size)))
    (keyReleased [e])
    (keyTyped [e])))

(defn game []
  (let [bike (ref (create-bike))
	frame (JFrame. "Tron")
	panel (game-panel frame bike)
	timer (Timer. turn-millis panel)]
    (doto panel
      (.setFocusable true)
      (.addKeyListener panel)
      (.setBackground (Color. 0 0 0)))
    (doto frame
      (.add panel)
      (.pack)
      (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
      (.setVisible true))
    (.start timer)
    [bike timer]))

(game)
