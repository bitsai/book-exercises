(defn create-runonce [function]
  (let [sentinel (Object.)
	result (atom sentinel)]
    (fn [& args]
      (locking sentinel
	(if (= @result sentinel)
	  (reset! result (function))
	  @result)))))

(def println-once
  (create-runonce #(println "there can be only one!")))

(println-once)
(println-once)

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
