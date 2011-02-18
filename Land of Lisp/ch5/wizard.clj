(ns wizard
  (:require [clojure.string :as str]))

(def nodes {'living-room '(you are in the living-room. a wizard is snoring loudly on the couch.)
	    'garden '(you are in a beautiful garden. there is a well in front of you.)
	    'attic '(you are in the attic. there is a giant welding torch in the corner.)})

(def edges {'living-room '((garden west door)
			   (attic upstairs ladder))
	    'garden '((living-room east door))
	    'attic '((living-room downstairs ladder))})

(def objects '(whiskey bucket frog chain))

(def object-locations (atom {'whiskey 'living-room
			     'bucket 'living-room
			     'chain 'garden
			     'frog 'garden}))

(def location (atom 'living-room))

(defn describe-location [location nodes]
  (nodes location))

;; Clojure doesn't seem to have an equivalent to quasi-quote
(defn describe-path [[_ direction thing]]
  (list 'there 'is 'a thing 'going direction 'from 'here.))

(defn describe-paths [location edges]
  (apply concat (map describe-path (edges location))))

(defn objects-at [loc objs obj-locs]
  (let [at-loc? #(= (obj-locs %) loc)]
    (filter at-loc? objs)))

(defn describe-objects [loc objs obj-locs]
  (let [describe-obj #(list 'you 'see 'a % 'on 'the 'floor.)]
    (apply concat (map describe-obj (objects-at loc objs obj-locs)))))

(defn look []
  (concat (describe-location @location nodes)
	  (describe-paths @location edges)
	  (describe-objects @location objects @object-locations)))

(defn walk [direction]
  (let [next (some #(if (= (second %) direction) %) (edges @location))]
    (if next
      (do
	(reset! location (first next))
	(look))
      '(you cannot go that way.))))

(defn pickup [object]
  (if (some #{object} (objects-at @location objects @object-locations))
    (do
      (swap! object-locations assoc object 'body)
      (list 'you 'are 'now 'carrying 'the object))
    '(you cannot get that.)))

(defn inventory []
  (cons 'ITEMS- (objects-at 'body objects @object-locations)))
