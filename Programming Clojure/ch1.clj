(println "hello world")
(defn hello [name] (str "Hello, " name))
(hello "Stu")
(hello "Clojure")
(str *1 " and " *2)
(/ 1 0)
(.printStackTrace *e)
;;(load-file "temp.clj")
#{}
(conj #{} "Stu")
(def visitors (ref #{}))
(alter visitors conj "Stu")
(dosync (alter visitors conj "Stu"))
(deref visitors)
@visitors
(defn hello-state
  [name]
  (dosync
   (let [past-visitor (@visitors name)]
     (if past-visitor
       (str "Welcome back, " name)
       (do
	 (alter visitors conj name)
	 (str "Hello, " name))))))
(hello-state "Rich")
(hello-state "Rich")

(require 'clojure.contrib.str-utils)
(require 'examples.introduction)
(take 10 examples.introduction/fibs)
(refer 'examples.introduction)
(take 10 fibs)
(use 'examples.introduction)
(take 10 fibs)
(use :reload-all 'examples.introduction)
(doc str)
(defn hello-doc
  "doc string"
  [name]
  (println (str "Hello, " name)))
(find-doc "reduce")
(use 'clojure.repl)
(source identity)
(use '[clojure.contrib.repl-utils :only (show)])
(show java.util.HashMap)
(show #{})
(ancestors (class [1 2 3]))
