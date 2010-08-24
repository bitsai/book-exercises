(new java.util.Random)
(def rnd (new java.util.Random))
(. rnd nextInt)
(. rnd nextInt 10)
(. Math PI)
(import '(java.util Random Locale)
	'(java.text MessageFormat))
Random
Locale
MessageFormat
(Random.)
Math/PI
(System/currentTimeMillis)
(.nextInt rnd)
(.getLocation (.getCodeSource (.getProtectionDomain (.getClass '(1 2)))))
(.. '(1 2) getClass getProtectionDomain getCodeSource getLocation)
(doto (System/getProperties)
  (.setProperty "name" "Stuart")
  (.setProperty "favoriteColor" "blue"))
(make-array String 5)
(seq (make-array String 5))
(find-doc "-array")
(defn painful-array []
  (let [arr (make-array String 5)]
    (aset arr 0 "pain")
    (aset arr 0 "to")
    (aset arr 0 "fill")
    (aset arr 0 "in")
    (aset arr 0 "array")
    arr))
(aget (painful-array) 0)
(alength (painful-array))
(to-array ["Easier" "array"])
(String/format "Week: %s Mileage: %d"
	       (to-array [2 26]))
(into-array String ["Easier" "array"])
(into-array ["Easier" "array"])
(def strs (into-array ["some" "strings"]))
(seq (amap strs idx _ (.toUpperCase (aget strs idx))))
(areduce strs idx ret 0 (max ret (.length (aget strs idx))))
(map .toUpperCase ["a" "short" "message"])
(map (memfn toUpperCase) ["a" "short" "message"])
(map #(.toUpperCase %) ["a" "short" "message"])
(instance? Integer 10)
(instance? Comparable 10)
(instance? String 10)
(format "%s ran %d miles today" "Stu" 8)
(import '(java.security MessageDigest))
(bean (MessageDigest/getInstance "SHA"))
(:digestLength (bean (MessageDigest/getInstance "SHA")))

(defn sum-to [n]
  (loop [i 1
	 sum 0]
    (if (<= i n)
      (recur (inc i) (+ i sum))
      sum)))
(sum-to 10)
(dotimes [_ 5] (time (sum-to 10)))
(defn integer-sum-to [n]
  (let [n (int n)]
    (loop [i (int 1)
	   sum (int 0)]
      (if (<= i n)
	(recur (inc i) (+ i sum))
	sum))))
(dotimes [_ 5] (time (integer-sum-to 10)))
(defn unchecked-sum-to [n]
  (let [n (int n)]
    (loop [i (int 1)
	   sum (int 0)]
      (if (<= i n)
	(recur (inc i) (unchecked-add i sum))
	sum))))
(dotimes [_ 5] (time (unchecked-sum-to 10)))
(sum-to 100000)
(integer-sum-to 100000)
(unchecked-sum-to 100000)
(defn better-sum-to [n]
  (reduce + (range 1 (inc n))))
(defn best-sum-to [n]
  (/ (* n (inc n)) 2))
(defn describe-class [c]
  {:name (.getName c)
   :final (java.lang.reflect.Modifier/isFinal (.getModifiers c))})
(set! *warn-on-reflection* true)
(defn describe-class [^Class c]
  {:name (.getName c)
   :final (java.lang.reflect.Modifier/isFinal (.getModifiers c))})
(describe-class StringBuffer)
(describe-class "foo")
(defn wants-a-string [^String s] (println s))
(wants-a-string "foo")
(wants-a-string 0)

(import '(org.xml.sax InputSource)
	'(org.xml.sax.helpers DefaultHandler)
	'(java.io StringReader)
	'(javax.xml.parsers SAXParserFactory))
(def print-elt-handler
     (proxy [DefaultHandler] []
       (startElement [uri local qname atts]
		     (println (format "Saw: %s" qname)))))
(defn demo-sax-parse [src handler]
  (.. SAXParserFactory newInstance newSAXParser
      (parse (InputSource. (StringReader. src))
	     handler)))
(demo-sax-parse "<foo><bar>Body of bar</bar></foo>" print-elt-handler)
(.start (Thread. (proxy [Runnable] [] (run [] (println "I ran!")))))
(proxy [Callable] [])
(.call (proxy [Callable] []))
(#(println "foo"))
(.run #(println "foo"))
(.call #(println "foo"))
(dotimes [i 5]
  (.start
   (Thread.
    (fn []
      (Thread/sleep (rand 500))
      (println (format "Finished %d on %s" i (Thread/currentThread)))))))

(spit "hello.out" "hello, world")
(try
  (throw (Exception. "failed"))
  (finally
   (println "clean up")))
(defn class-available? [class-name]
  (Class/forName class-name))
(class-available? "borg.util.Assimilate")
(defn class-available? [class-name]
  (try
    (Class/forName class-name) true
    (catch ClassNotFoundException _ false)))
(class-available? "borg.util.Assimilate")
(class-available? "java.lang.String")
