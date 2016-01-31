(ns day-2)

(defmacro unless [test body1 body2]
  `(if (not ~test)
     ~body1
     ~body2))

;; day-2> (unless true
;;                (println "A!")
;;                (println "B!"))
;; B!
;; nil
;; day-2> (unless false
;;                (println "A!")
;;                (println "B!"))
;; A!
;; nil

(defprotocol Clock
  (now [c]))

(defrecord SimpleClock []
  Clock
  (now [_]
    (System/currentTimeMillis)))

;; day-2> (now (->SimpleClock))
;; 1454206377460
