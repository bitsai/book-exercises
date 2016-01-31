(ns day-1)

(defn big [st n]
  (> (count st) n))

;; day-1> (big "hello world" 9)
;; true
;; day-1> (big "hello world" 10)
;; true
;; day-1> (big "hello world" 11)
;; false

(defn collection-type [col]
  (cond
    (list? col)   :list
    (map? col)    :map
    (vector? col) :vector
    :else         :unknown))

;; day-1> (collection-type '())
;; :list
;; day-1> (collection-type {})
;; :map
;; day-1> (collection-type [])
;; :vector
;; day-1> (collection-type #{})
;; :unknown
