(use 'step-2-complete 'step-3-complete)
(let [[has-run-fn reset-fn once-fn] (runonce #(println "boo!"))]
  (def #^{:has-run-fn has-run-fn :reset-fn reset-fn :doc "doc"}
    boo once-fn))
(boo)
(boo)
(meta #'boo)
(defn has-run? [v]
  ((:has-run-fn (meta v))))
(has-run? #'boo)
(has-run boo)
(defmacro has-run? [f]
  `((:has-run-fn (meta (var ~f)))))
(has-run? boo)
(defmacro reset [f]
  `((:reset-fn (meta (var ~f)))))
(reset boo)
(has-run? boo)
(let [[has-run-fn reset-fn once-fn] (runonce #(println "boo!"))]
  (def #^{:has-run-fn has-run-fn :reset-fn reset-fn :doc "doc"}
    boo once-fn))
(defmacro deftarget [sym doc & forms]
  (let [[has-run-fn reset-fn once-fn] (runonce #(println "boo!"))]
    (def #^{:has-run-fn has-run-fn
	    :reset-fn reset-fn
	    :doc "doc"}
      boo once-fn)))
(defmacro deftarget [sym doc & forms]
  `(let [[has-run-fn reset-fn once-fn] (runonce (fn [] ~@forms))]
     (def #^{:has-run-fn has-run-fn
	     :reset-fn reset-fn
	     :doc doc}
       ~sym once-fn)))
(defmacro deftarget [sym doc & forms]
  `(let [[has-run-fn reset-fn once-fn] (runonce (fn [] ~@forms))]
     (def ~(with-meta sym {:has-run-fn has-run-fn
			   :reset-fn reset-fn
			   :doc ~doc})
       once-fn)))
(defmacro deftarget [sym doc & forms]
  `(let [[has-run-fn# reset-fn# once-fn#] (runonce (fn [] ~@forms))]
     (def ~(with-meta sym {:has-run-fn has-run-fn#
			   :reset-fn reset-fn#
			   :doc doc})
       once-fn#)))
(gensym)
(gensym "has-run-fn__")
(defmacro deftarget [sym doc & forms]
  (let [has-run-fn (gensym "hr-")
	reset-fn (gensym "rf-")]
    `(let [[~has-run-fn ~reset-fn once-fn#] (runonce (fn [] ~@forms))]
       (def ~(with-meta sym {:has-run-fn has-run-fn
			     :reset-fn reset-fn
			     :doc doc})
	 once-fn#))))
(binding [*print-meta* true]
  (prn (macroexpand-1
	'(deftarget foo "docstr" (println "hello")))))
(deftarget foo "demo"
  (println "there can be only one"))
(foo)
(has-run? foo)
(reset foo)
(has-run? foo)
(def echo
  (instantiate-task ant-project "echo" {:message "hello"}))
(.execute echo)
(echo {:message "hello"})
(defn ant-echo [props]
  (let [task (instantiate-task ant-project "echo" props)]
    (.execute task)
    task))
(defmacro define-ant-task [clj-name ant-name]
  `(defn ~clj-name [props#]
     (let [task# (instantiate-task ant-project ~(name ant-name) props#)]
       (.execute task#)
       task#)))
(define-ant-task mkdir mkdir)
(mkdir {:dir (java.io.File. "foo")})
(defn task-names []
  (map symbol (sort (.. ant-project getTaskDefinitions keySet))))
(task-names)
(defmacro define-all-ant-tasks []
  `(do ~@(map (fn [n] `(define-ant-task ~n ~n)) (task-names))))
(macroexpand-1 '(define-all-ant-tasks))
(define-all-ant-tasks)
(defn safe-ant-name [n]
  (if (resolve n) (symbol (str "ant-" n)) n))
(defmacro define-all-ant-tasks []
  `(do ~@(map (fn [n] `(define-ant-task ~(safe-ant-name n) ~n)) (task-names))))
(define-all-ant-tasks)
(sleep {:seconds 5})
