(ns day-3)

(def accounts (ref [{:balance 1.0}
                    {:balance 2.0}
                    {:balance 3.0}]))

(defn- update-balance [accts idx delta]
  (update-in accts [idx :balance] + delta))

(defn debit! [idx x]
  (dosync
   (alter accounts update-balance idx (- x))))

(defn credit! [idx x]
  (dosync
   (alter accounts update-balance idx x)))

;; day-3> (clojure.pprint/pprint accounts)
;; #<Ref@4889e2b2: [{:balance 1.0} {:balance 2.0} {:balance 3.0}]>
;; nil
;; day-3> (debit! 0 1.0)
;; [{:balance 0.0} {:balance 2.0} {:balance 3.0}]
;; day-3> (credit! 0 5.0)
;; [{:balance 5.0} {:balance 2.0} {:balance 3.0}]
;; day-3> (clojure.pprint/pprint accounts)
;; #<Ref@4889e2b2: [{:balance 5.0} {:balance 2.0} {:balance 3.0}]>
;; nil

(defn sleeping-barber []
  (let [haircuts (atom 0)
        running (atom true)
        ;; waiting room has 3 chairs
        waiting (java.util.concurrent.LinkedBlockingQueue. 3)]
    ;; customers
    (future (while @running
              ;; arrive every 10-30 milliseconds
              (Thread/sleep (+ 10 (rand-int (inc 20))))
              ;; enter if waiting room isn't full
              (.offer waiting "customer")))
    ;; barber
    (future (while @running
              ;; sleep until customer arrives
              (.take waiting)
              ;; haircuts take 20 milliseconds
              (Thread/sleep 20)
              (swap! haircuts inc)))
    ;; run for 10 seconds
    (Thread/sleep 10000)
    (reset! running false)
    @haircuts))

;; day-3> (sleeping-barber)
;; 407
