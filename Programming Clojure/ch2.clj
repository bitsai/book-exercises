42
[1 2 3]
(+ 1 2)
(concat [1 2] [3 4])
(+ 1 2 3)
(+)
(- 10 5)
(* 3 10 10)
(> 5 2)
(>= 5 5)
(< 5 2)
(= 5 2)
(/ 22 7)
(class (/ 22 7))
(/ 22.0 7)
(quot 22 7)
(rem 22 7)
(+ 1 (/ 0.00001 100000000000000000000))
(+ 1 (/ 0.00001M 100000000000000000000))
(class (* 1000 1000 1000))
(class (* 1000 1000 1000 1000 1000 1000 1000 1000 1000))
"a\nmultiline string"
"another
multiline string"
(println "a\nmultiline string")
(.toUpperCase "hello")
(str 1 2 nil 3)
(str \h \e \l \l \o)
(Character/toUpperCase \s)
(interleave "abc" "xyz")
(str (interleave "abc" "xyz"))
(apply str (interleave "abc" "xyz"))
(apply str (take-nth 2 "axbycz"))
(if () "clojure" "common lisp")
(if 0 "clojure" "c")
(true? true)
(true? "foo")
(zero? 0.0)
(zero? (/ 22 7))
(find-doc #"\?$")
(def inventors {"lisp" "mccarthy", "clojure" "hickey"})
(inventors "lisp")
(inventors "foo")
(get inventors "lisp" "dunno")
(get inventors "foo" "dunno")
:foo
foo
(def inventors-keywords {:lisp "mccarthy", :clojure "hickey"})
(inventors-keywords :clojure)
(:clojure inventors-keywords)
(defstruct book :title :author)
(def b (struct book "anathem" "stephenson"))
b
(:title b)
(struct-map book :copyright 2008 :title "anathem")

'(1 2)
(quote (1 2))

(str "hello" " " "world")
(string? "hello")
(keyword? :hello)
(symbol? 'hello)
(defn greeting
  "doc string"
  [name]
  (str "Hello, " name))
(greeting "world")
(doc greeting)
(greeting)
(defn greeting-arity
  ([] (greeting-arity "world"))
  ([name] (str "Hello, " name)))
(greeting-arity)
(defn date [person-1 person-2 & chaperones]
  (println person-1 "and" person-2
	   "went out with" (count chaperones) "chaperones."))
(date "romeo" "juliet" "friar" "nurse")

(defn indexable-word? [word]
  (> (count word) 2))
(use '[clojure.string :only (split)])
(filter indexable-word? (split "a fine day it is" #"\W+"))
(filter (fn [w] (> (count w) 2)) (split "a fine day it is" #"\W+"))
(filter #(> (count %) 2) (split "a fine day it is" #"\W+"))
(defn indexable-words [text]
  (let [indexable-word? #(> (count %) 2)]
    (filter indexable-word? (split text #"\W+"))))
(indexable-words "a fine day it is")
(defn make-greeter [prefix]
  #(str prefix ", " %))
(def hello-greeting (make-greeter "Hello"))
(def aloha-greeting (make-greeter "Aloha"))
(hello-greeting "world")
(aloha-greeting "world")
((make-greeter "Howdy") "pardner")

(def foo 10)
foo
(var foo)
#'foo
(defn triple [number] (* 3 number))
(triple 10)
(defn square-corners [bottom left size]
  (let [top (+ bottom size)
	right (+ left size)]
    [[bottom left] [top left] [top right] [bottom right]]))
(defn greet-author-1 [author]
  (println "Hello," (:first-name author)))
(greet-author-1 {:last-name "vinge" :first-name "vernor"})
(defn greet-author-2 [{fname :first-name}]
  (println "Hello," fname))
(greet-author-2 {:last-name "vinge" :first-name "vernor"})
(let [[x y] [1 2 3]]
  [x y])
(let [[_ _ z] [1 2 3]]
  z)
(let [[_ _ z] [1 2 3]]
  _)
(let [[x y :as coords] [1 2 3 4 5 6]]
  (str "x: " x ", y: " y ", total dimentions " (count coords)))
(use '[clojure.string :only (split join)])
(defn ellipsize [words]
  (let [[w1 w2 w3] (split words #"\s+")]
    (join " " [w1 w2 w3 "..."])))
(ellipsize "the quick brown fox jumps over the lazy dog.")
(let [{:keys [a b c]} {:a "1", :b "2", :c "3"}]
  [a b c])
(let [{:strs [a b c]} {"a" "1", "b" "2", "c" "3"}]
  [a b c])
(let [{:syms [a b c]} {'a "1", 'b "2", 'c "3"}]
  [a b c])
(def foo 10)
(resolve 'foo)
(in-ns 'myapp)
String
(clojure.core/use 'clojure.core)
File/separator
java.io.File/separator
(import '(java.io InputStream File))
File/separator
(require 'clojure.contrib.math)
(clojure.contrib.math/round 1.7)
(round 1.7)
(use 'clojure.contrib.math)
(use '[clojure.contrib.math :only (round)])
(round 1.2)
(use :reload '[clojure.contrib.math :only (round)])
(use :reload 'examples.exploring)
(use :reload-all 'examples.exploring)
(ns examples.exploring
  (:use examples.utils clojure.contrib.str-utils)
  (:import (java.io File)))
(find-doc "ns-")

(defn is-small? [number]
  (if (< number 100) "yes"))
(is-small? 50)
(is-small? 50000)
(defn is-small? [number]
  (if (< number 100) "yes" "no"))
(is-small? 50000)
(defn is-small? [number]
  (if (< number 100)
    "yes"
    (do
      (println "saw a big number" number)
      "no")))
(is-small? 200)
(loop [result []
       x 5]
  (if (zero? x)
    result
    (recur (conj result x) (dec x))))
(defn countdown [result x]
  (if (zero? x)
    result
    (recur (conj result x) (dec x))))
(countdown [] 5)
(into [] (take 5 (iterate dec 5)))
(into [] (drop-last (reverse (range 6))))
(vec (reverse (rest (range 6))))
(defn indexed [coll] (map vector (iterate inc 0) coll))
(indexed "abcde")
(defn index-filter [pred coll]
  (when pred
    (for [[idx elt] (indexed coll) :when (pred elt)] idx)))
(index-filter #{\a \b} "abcdbbb")
(index-filter #{\a \b} "xyz")
(defn index-of-any [pred coll]
  (first (index-filter pred coll)))
(index-of-any #{\z \a} "zzabyycdxx")
(index-of-any #{\b \y} "zzabyycdxx")
(nth (index-filter #{:h} [:t :t :h :t :h :t :t :t :h :h]) 2)

(def stu {:name "stu" :email "stu@tr.com"})
(def serial-stu (with-meta stu {:serial true}))
(= stu serial-stu)
(identical? stu serial-stu)
(meta stu)
(meta serial-stu)
;; deprecated: ^stu
;; deprecated: ^serial-stu
(def stu-with-addr (assoc serial-stu :state "nc"))
;; deprecated: ^stu-with-addr
(meta #'str)
(defn ^{:tag String} shout [^{:tag String} s] (.toUpperCase s))
(meta #'shout)
(shout 1)
(defn ^String shout [^String s] (.toUpperCase s))
(defn shout
  ([s] (.toUpperCase s))
  {:tag String})
(def ^{:testdata true} foo (with-meta [1 2 3] {:order :asc}))
(meta #'foo)
(meta foo)
