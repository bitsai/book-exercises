(defn my-print [ob]
  (.write *out* ob))
(defn my-println [ob]
  (my-print ob)
  (.write *out* "\n"))
(my-println "hello!")
(my-println nil)
(defn my-print [ob]
  (cond
   (nil? ob) (.write *out* "nil")
   (string? ob) (.write *out* ob)))
(my-println nil)
(my-println [1 2 3])
(require 'clojure.string)
(defn my-print-vector [ob]
  (.write *out* "[")
  (.write *out* (clojure.string/join " " ob))
  (.write *out* "]"))
(defn my-print [ob]
  (cond
   (vector? ob) (my-print-vector ob)
   (nil? ob) (.write *out* "nil")
   (string? ob) (.write *out* ob)))
(my-println [1 2 3])

(defmulti my-print class)
(my-println "foo")
(defmethod my-print String [s]
  (.write *out* s))
(my-println "stu")
(defmethod my-print nil [s]
  (.write *out* "nil"))
(defmethod my-print Number [n]
  (.write *out* (.toString n)))
(my-println 42)
(isa? Integer Number)
(defmethod my-print :default [s]
  (.write *out* "#<")
  (.write *out* (.toString s))
  (.write *out* ">"))
(my-println (java.sql.Date. 0))
(my-println (java.util.Random.))
(defmulti my-print class :default :everything-else)
(defmethod my-print String [s]
  (.write *out* s))
(defmethod my-print :everything-else [_]
  (.write *out* "Not implemented yet..."))

(require 'clojure.string)
(defmethod my-print java.util.Collection [c]
  (.write *out* "(")
  (.write *out* (clojure.string/join " " c))
  (.write *out* ")"))
(my-println (take 6 (cycle [1 2 3])))
(my-println [1 2 3])
(defmethod my-print clojure.lang.IPersistentVector [c]
  (.write *out* "[")
  (.write *out* (clojure.string/join " " c))
  (.write *out* "]"))
(my-println [1 2 3])
(prefer-method my-print
	       clojure.lang.IPersistentVector java.util.Collection)
(my-println (take 6 (cycle [1 2 3])))
(my-println [1 2 3])

(ns my.account)
(defstruct account :id :tag :balance)
:Checking
::Checking
(struct account 1 ::my.account/Savings 100M)
(alias 'acc 'my.account)
(struct account 1 ::acc/Savings 100M)
(def test-savings (struct account 1 ::acc/Savings 100M))
(def test-checking (struct account 2 ::acc/Checking 100M))
(defmulti interest-rate :tag)
(defmethod interest-rate ::acc/Checking [_] 0M)
(defmethod interest-rate ::acc/Savings [_] 0.05M)
(interest-rate test-savings)
(interest-rate test-checking)
(defmulti account-level :tag)
(defmethod account-level ::acc/Checking [acct]
  (if (>= (:balance acct) 5000) ::acc/Premium ::acc/Basic))
(defmethod account-level ::acc/Savings [acct]
  (if (>= (:balance acct) 1000) ::acc/Premium ::acc/Basic))
(account-level (struct account 1 ::acc/Savings 2000M))
(account-level (struct account 1 ::acc/Checking 2000M))
(defmulti service-charge account-level)
(defmethod service-charge ::Basic [acct]
  (if (= (:tag acct) ::Checking) 25 10))
(defmethod service-charge ::Premium [_] 0)
(defmulti service-charge (fn [acct] [(account-level acct) (:tag acct)]))
(defmethod service-charge [::acc/Basic ::acc/Checking] [_] 25)
(defmethod service-charge [::acc/Basic ::acc/Savings] [_] 10)
(defmethod service-charge [::acc/Premium ::acc/Checking] [_] 0)
(defmethod service-charge [::acc/Premium ::acc/Savings] [_] 0)
(service-charge {:tag ::acc/Checking :balance 1000})
(service-charge {:tag ::acc/Savings :balance 1000})
(derive ::acc/Savings ::acc/Account)
(derive ::acc/Checking ::acc/Account)
(isa? ::acc/Savings ::acc/Account)
(defmulti service-charge (fn [acct] [(account-level acct) (:tag acct)]))
(defmethod service-charge [::acc/Basic ::acc/Checking] [_] 25)
(defmethod service-charge [::acc/Basic ::acc/Savings] [_] 10)
(defmethod service-charge [::acc/Premium ::acc/Account] [_] 0)

(use '[clojure.inspector :only (inspect inspect-tree)])
(inspect-tree (System/getProperties))
(inspect-tree {:clojure {:creator "Rich" :runs-on-jvm true}})
(use :reload '[clojure.test :only (is)])
(is (string? 10))
(is (instance? java.util.Collection "foo"))
(is (= "power" "wisdom"))
(defmulti my-class identity)
(defmethod my-class nil [_] nil)
(defmethod my-class :default [x] (.getClass x))
