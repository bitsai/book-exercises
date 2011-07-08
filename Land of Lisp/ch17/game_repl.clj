(ns game-repl
  (:require [clojure.string :as str]))

(def *allowed-commands* (atom '[look walk pickup inventory]))

(defn game-read []
  (let [[action & args] (read-string (str "(" (read-line) ")"))
	quote-it (fn [x] (list 'quote x))]
    (cons action (map quote-it args))))

(defn game-eval [sexp]
  (let [[action & _] sexp]
    (if (some #{action} @*allowed-commands*)
      (eval sexp)
      '[I do not know that command.])))

(defn tweak-text [lst caps? lit?]
  (when lst
    (let [[item & rest] lst]
      (cond (= \space item) (cons item (tweak-text rest caps? lit?))
            (some #{item} "!?.") (cons item (tweak-text rest true lit?))
            (= \" item) (tweak-text rest caps? (not lit?))
            lit? (cons item (tweak-text rest false lit?))
            (or caps? lit?) (cons (str/upper-case item)
                                  (tweak-text rest false lit?))
            :else (cons (str/lower-case item)
                        (tweak-text rest false false))))))

(defn game-print [lst]
  (let [s (pr-str lst)
	trimmed (subs s 1 (dec (count s)))
	tweaked (tweak-text trimmed true false)]
    (println (apply str tweaked))))

(defn game-repl []
  (let [cmd (game-read)]
    (when-not (= 'quit (first cmd))
      (game-print (game-eval cmd))
      (recur))))
