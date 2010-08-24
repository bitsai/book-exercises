(use '[clojure.contrib.except])

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

(defn instantiate-task [project name]
  (let [task (.createTask project name)]
    (throw-if (nil? task)
	      IllegalArgumentException (str "No task named " name))
    (doto task
      (.init)
      (.setProject project))))

(def echo-task (instantiate-task ant-project "echo"))
(.setMessage echo-task "some msg")
(instantiate-task ant-project "echo" {:message "hello"})
(import '(java.beans Introspector))
(Introspector/getBeanInfo (class echo-task))
(.getPropertyDescriptors *1)
(def prop-descs *1)
(count prop-descs)
(bean (first prop-descs))
(import '(java.beans Introspector))
(defn property-descriptor [inst prop-name]
  (first
   (filter #(= (name prop-name) (.getName %))
	   (.getPropertyDescriptors
	    (Introspector/getBeanInfo (class inst))))))
(bean (property-descriptor echo-task :message))
(use '[clojure.contrib.except :only (throw-if)])
(defn set-property! [inst prop value]
  (let [pd (property-descriptor inst prop)]
    (throw-if (nil? pd) (str "No such property " prop))
    (.invoke (.getWriteMethod pd) inst (into-array [value]))))
(set-property! echo-task :message "a new msg!")
(.execute echo-task)
(defn set-properties! [inst prop-map]
  (doseq [[k v] prop-map] (set-property! inst k v)))
(set-properties! echo-task {:message "yet another msg"})
(.execute echo-task)
(defn instantiate-task [project name props]
  (let [task (.createTask project name)]
    (throw-if (nil? task) (str "No task named " name))
    (doto task
      (.init)
      (.setProject project)
      (set-properties! props))
    task))
(def echo-with-msg
  (instantiate-task ant-project "echo" {:message "hello"}))
(.execute echo-with-msg)
