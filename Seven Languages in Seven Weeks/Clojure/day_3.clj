(ns day-3)

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
