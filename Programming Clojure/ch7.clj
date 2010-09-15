(if (= 1 1) (println "yep"))
(defn unless [expr form]
  (if expr nil form))
(unless false (println "print"))
(unless true (println "don't print"))
(defn unless [expr form]
  (println "starting...")
  (if expr nil form))
(unless false (println "print"))
(unless true (println "not print"))
(defmacro unless [expr form]
  (list 'if expr nil form))
(unless false (println "print"))
(if false nil (println "print"))
(unless false (println "print"))
(unless true (println "not print"))
(defmacro unless [expr form]
  (list 'if expr nil form))
(macroexpand-1 '(unless false (println "print")))
(defmacro bad-unless [expr form]
  (list 'if 'expr nil form))
(macroexpand-1 '(bad-unless false (println "print")))
(bad-unless false (println "print"))
(macroexpand-1 '(.. arm getHand getFinger))
(macroexpand '(.. arm getHand getFinger))
(macroexpand '(and 1 2 3))
(unless false (println "this") (println "that"))
(when-not false (println "this") (println "that"))
(defmacro when-not [test & body]
  (list 'if test nil (cons 'do body)))
(macroexpand-1 '(when-not false (print "1") (print "2")))

(defmacro chain [x form]
  (list '. x form))
(defmacro chain
  ([x form] (list '. x form))
  ([x form & more] (concat (list 'chain (list '. x form)) more)))
(macroexpand '(chain arm getHand))
(macroexpand '(chain arm getHand .getFinger))
(defmacro chain
  ([x form] (. ${x} ${form}))
  ([x form & more] (chain (. ${x} ${form}) ${more})))
(defmacro chain [x form]
  `(. ~x ~form))
(macroexpand '(chain arm getHand))
(defmacro chain
  ([x form] `(. ~x ~form))
  ([x form & more] `(chain (. ~x ~form) ~more)))
(macroexpand '(chain arm getHand getFinger))
(defmacro chain
  ([x form] `(. ~x ~form))
  ([x form & more] `(chain (. ~x ~form) ~@more)))
(macroexpand '(chain arm getHand getFinger))
(time (str "a" "b"))
(let [start (System/nanoTime)
      result (str "a" "b")]
  {:result result :elapsed (- (System/nanoTime) start)})
(defmacro bench [expr]
  `(let [start (System/nanoTime)
	 result ~expr]
     {:result result :elapsed (- (System/nanoTime) start)}))
(bench (str "a" "b"))
(macroexpand-1 '(bench (str "a" "b")))
`foo#
(defmacro bench [expr]
  `(let [start# (System/nanoTime)
	 result# ~expr]
     {:result result# :elapsed (- (System/nanoTime) start#)}))
(bench (str "a" "b"))

(defmacro and
  ([] true)
  ([x] x)
  ([x & rest]
     `(let [and# ~x]
	(if and# (and ~@rest) and#))))
(and 1 0 nil false)
(or 1 0 nil false)
(comment
  (load-file "src/inspector.clj")
  (refer 'inspector)
  (inspect-tree {:a 1, :b [2 {:c 3, :d [4]}]})
  (inspect-table [[1][2][3]])
  )
(def person (create-struct :first :last))
(defmacro defstruct
  [name & keys]
  `(def ~name (create-struct ~@keys)))
(def a)
(def b)
(def c)
(def d)
(declare a b c d)
(defmacro declare
  [& names] `(do ~@(map #(list 'def %) names)))
(#(list 'def %) 'a)
(map #(list 'def %) '[a b c d])
`(do ~@(map #(list 'def %) '[a b c d]))
(macroexpand-1 '(declare a b c d))
Math/PI
(Math/pow 10 3)
(def PI Math/PI)
(defn pow [b e] (Math/pow b e))
(use '[clojure.contrib.import-static :only (import-static)])
(import-static java.lang.Math PI pow)
PI
(pow 10 3)
(def slow-calc (delay (Thread/sleep 5000) "done!"))
(force slow-calc)
(force slow-calc)
(with-out-str (print "hello, ") (print "world"))
(defmacro with-out-str
  [& body]
  `(let [s# (java.io.StringWriter.)]
     (binding [*out* s#]
       ~@body
       (str s#))))
(assert (= 1 1))
(assert (= 1 2))
(defn bench-fn [f]
  (let [start (System/nanoTime)
	result (f)]
    {:result result :elapsed (- (System/nanoTime) start)}))
(bench (+ 1 2))
(bench-fn #(+ 1 2))
