(ns day3)

(def accounts (ref {1 {:balance 1.0}
                    2 {:balance 2.0}
                    3 {:balance 3.0}}))

(defn update-balance [accts id delta]
  (update-in accts [id :balance] + delta))

(defn debit [id x]
  (dosync
   (alter accounts update-balance id (- x))))

(defn credit [id x]
  (dosync
   (alter accounts update-balance id x)))

;; TODO: sleeping barber
