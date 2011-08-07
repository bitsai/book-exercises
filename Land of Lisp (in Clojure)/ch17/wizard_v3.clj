(ns wizard-v3
  (:require [clojure.string :as str]))

(def nodes '{living-room [you are in the living-room. a wizard is snoring
                          loudly on the couch.]
             garden [you are in a beautiful garden. there is a well in
                     front of you.]
             attic [you are in the attic. there is a giant welding torch
                    in the corner.]})

(def edges '{living-room [[garden west door]
                          [attic upstairs ladder]]
             garden [[living-room east door]]
             attic [[living-room downstairs ladder]]})

(def objects '[whiskey bucket frog chain])

(def *object-locations* (atom '{whiskey living-room
                                bucket living-room
                                chain garden
                                frog garden}))

(def *location* (atom 'living-room))

(def *allowed-commands* (atom '[look walk pickup inventory]))

(def *chain-welded?* (atom false))

(def *bucket-filled?* (atom false))

(defn describe-location [location nodes]
  (nodes location))

(defn describe-path [edge]
  (let [[_ direction object] edge]
    ['there 'is 'a object 'going direction 'from 'here.]))

(defn describe-paths [location edges]
  (mapcat describe-path (edges location)))

(defn objects-at [loc objs obj-locs]
  (let [at-loc? (fn [obj] (= loc (obj-locs obj)))]
    (filter at-loc? objs)))

(defn describe-objects [loc objs obj-locs]
  (let [describe-obj (fn [obj] ['you 'see 'a obj 'on 'the 'floor.])]
    (mapcat describe-obj (objects-at loc objs obj-locs))))

(defn look []
  (concat (describe-location @*location* nodes)
	  (describe-paths @*location* edges)
	  (describe-objects @*location* objects @*object-locations*)))

(defn find* [x coll f]
  (first (filter #(= x (f %)) coll)))

(defn walk [direction]
  (let [next (find* direction (edges @*location*) second)]
    (if next
      (do (reset! *location* (first next))
          (look))
      '[you cannot go that way.])))

(defn pickup [object]
  (let [objs-at-loc (objects-at @*location* objects @*object-locations*)]
    (if (some #{object} objs-at-loc)
      (do (swap! *object-locations* assoc object 'body)
          ['you 'are 'now 'carrying 'the object])
      '[you cannot get that.])))

(defn inventory []
  (cons 'ITEMS- (objects-at 'body objects @*object-locations*)))

(defn game-read []
  (let [[action & args] (read-string (str "(" (read-line) ")"))
	quote-it (fn [x] (list 'quote x))]
    (cons action (map quote-it args))))

(defn game-eval [sexp]
  (if (some #{(first sexp)} @*allowed-commands*)
    (eval sexp)
    '[i do not know that command.]))

(defn tweak-text [lst caps? lit?]
  (when lst
    (let [[item & rest] lst]
      (cond (= \space item) (cons item (tweak-text rest caps? lit?))
            (some #{item} "!?.") (cons item (tweak-text rest true lit?))
            (= \" item) (tweak-text rest caps? (not lit?))
            lit? (cons item (tweak-text rest false lit?))
            caps? (cons (str/upper-case item)
                        (tweak-text rest false lit?))
            :else (cons (str/lower-case item)
                        (tweak-text rest false false))))))

(defn game-print [lst]
  (let [s (pr-str lst)
	trimmed (subs s 1 (dec (count s)))
	tweaked (tweak-text trimmed true false)]
    (println (reduce str tweaked))))

(defn game-repl []
  (let [cmd (game-read)]
    (when-not (= 'quit (first cmd))
      (game-print (game-eval cmd))
      (recur))))

(defn have? [object]
  (some #{object} (inventory)))

(defmacro game-action [command subj obj place & body]
  `(do (defn ~command [~'subject ~'object]
         (if (and (= '~place @*location*)
                  (= '~subj ~'subject)
                  (= '~obj ~'object)
                  (have? '~subj))
           ~@body
           '[~'you ~'cannot ~command ~'like ~'that.]))
       (swap! *allowed-commands* conj '~command)))

(game-action weld chain bucket attic
             (if (and (have? 'bucket) (not @*chain-welded?*))
               (do (reset! *chain-welded?* true)
                   '[the chain is now securely welded to the bucket.])
               '[you do not have a bucket.]))

(game-action dunk bucket well garden
             (if @*chain-welded?*
               (do (reset! *bucket-filled?* true)
                   '[the bucket is now full of water.])
               '[the water level is too low to reach.]))

(game-action splash bucket wizard living-room
             (cond (not @*bucket-filled?*) '[the bucket has nothing in it.]
                   (have? 'frog) '[the wizard awakens and sees that you
                                   stole his frog. he is so upset he
                                   banishes you to the netherworlds- you
                                   lose! the end.]
                   :else '[the wizard awakens from his slumber and greets
                           you warmly. he hands you a magic low-carb
                           donut- you win! the end.]))
