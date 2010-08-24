(def mkdir-task (org.apache.tools.ant.taskdefs.Mkdir.))
(map #(.getName %) (.getMethods (class mkdir-task)))
(.execute mkdir-task)
(.setDir mkdir-task "sample-dir")
(filter #(= "setDir" (.getName %))
	(.getMethods (class mkdir-task)))
(.setDir mkdir-task (java.io.File. "sample-dir"))
(.execute mkdir-task)
(def project (org.apache.tools.ant.Project.))
(def logger (org.apache.tools.ant.NoBannerLogger.))
(.setOutputPrintStream logger System/out)
(.setErrorPrintStream logger System/err)
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
ant-project
(bean ant-project)
(keys (bean ant-project))
(:buildListeners (bean ant-project))
(def mkdir-task (org.apache.tools.ant.taskdefs.Mkdir.))
(def echo-task (.createTask ant-project "echo"))
(.setMessage echo-task "hello ant")
(.execute echo-task)
(defn instantiate-task [project name]
  (let [task (.createTask project name)]
    (doto task
      (.init)
      (.setProject project))))
(def echo-task (instantiate-task ant-project "echo"))
(.setMessage echo-task "echo from instantiate-task")
(.execute echo-task)
(instantiate-task ant-project "sisyphus")
(use '[clojure.contrib.except :only (throw-if)])
(defn safe-instantiate-task [project name]
  (let [task (.createTask project name)]
    (throw-if (nil? task)
	      IllegalArgumentException (str "No task named " name))
    (doto task
      (.init)
      (.setProject project))))
(safe-instantiate-task ant-project "sisyphus")
