(ns step-3-complete)

(defn runonce [function]
  (let [sentinel (Object.)
	result (atom sentinel)
	reset-fn (fn [] (reset! result sentinel) nil)
	has-run? #(not= @result sentinel)]
    [has-run?
     reset-fn
     (fn [& args]
       (locking sentinel
	 (if (= @result sentinel)
	   (reset! result (function))
	   @result)))]))
