(ns day2)

(defmacro unless [test body1 body2]
  `(if (not ~test)
     ~body1
     ~body2))

(defprotocol Clock
  (now [c]))

(defrecord SimpleClock []
  Clock
  (now [_]
    (System/currentTimeMillis)))
