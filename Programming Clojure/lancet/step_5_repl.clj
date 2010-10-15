(defmulti coerce
  (fn [dest-class src-inst] [dest-class (class src-inst)]))
(defmethod coerce [java.io.File String] [_ str]
  (java.io.File. str))
(coerce java.io.File "foo")
(defmethod coerce [Boolean/TYPE String] [_ str]
  (contains? #{"on" "yes" "true"} (.toLowerCase str)))
(coerce Boolean/TYPE "no")
(coerce Boolean/TYPE "yes")
(coerce Boolean/TYPE "TRUE")
(defmethod coerce [Boolean/TYPE String] [_ str]
  (contains? #{"totally" "way"} (.toLowerCase str)))
(defmethod coerce :default [dest-cls obj] (cast dest-cls obj))
(coerce Comparable "hello")
(coerce java.io.File (java.util.Random.))
(use '[clojure.contrib.except :only (throw-if)])
(import '(java.beans Introspector))
(def
  #^{:doc "Dummy ant project to keep Ant tasks happy"}
  ant-project
  (let [proj (org.apache.tools.ant.Project.)
	logger (org.apache.tools.ant.NoBannerLogger.)]
    (doto logger
      (.setMessageOutputLevel org.apache.tools.ant.Project/MSG_INFO)
      (.setOutputPrintStream System/out)
      (.setErrorPrintStream System/err))
    (doto proj
      (.init)
      (.addBuildListener logger))))
(defn property-descriptor [inst prop-name]
  (first
   (filter #(= (name prop-name) (.getName %))
	   (.getPropertyDescriptors
	    (Introspector/getBeanInfo (class inst))))))
(defn get-property-class [write-method]
  (first (.getParameterTypes write-method)))
(defn set-property! [inst prop value]
  (let [pd (property-descriptor inst prop)]
    (throw-if (nil? pd) (str "No such property " prop))
    (let [write-method (.getWriteMethod pd)
	  dest-class (get-property-class write-method)]
      (.invoke
       write-method inst (into-array [(coerce dest-class value)])))))
(defn set-properties! [inst prop-map]
  (doseq [[k v] prop-map] (set-property! inst k v)))
(defn instantiate-task [project name props]
  (let [task (.createTask project name)]
    (throw-if (nil? task) (str "No task named " name))
    (doto task
      (.init)
      (.setProject project)
      (set-properties! props))
    task))
(def mkfoo
  (instantiate-task ant-project "mkdir" {:dir "foo"}))
(.execute mkfoo)