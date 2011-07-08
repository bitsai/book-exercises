(prn "foo")
(do (prn "this")
    (prn "is")
    (prn "a")
    (prn "test"))
(do (pr "this")
    (pr "is")
    (pr "a")
    (pr "test"))

(defn say-hello []
  (prn "Please type your name:")
  (let [name (read)]
    (prn "Nice to meet you,")
    (prn name)))
(say-hello)

(defn add-five []
  (prn "Please enter a number:")
  (let [num (read)]
    (prn "When I add five I get")
    (prn (+ num 5))))
(add-five)
(pr 3)
(pr 3.4)
(pr 'foo)
(pr "foo")
(pr \a)

(print 3)
(print 3.4)
(print 'foo)
(print "foo")
(print \a)
(do (print "This sentence will be interrupted")
    (print \newline)
    (print "by an annoying newline character."))
(defn say-hello []
  (println "Please type your name:")
  (let [name (read-line)]
    (println "Nice to meet you," name)))
(say-hello)

(quote (+ 1 2))
(+ 1 2)
(def foo '(+ 1 2))
(eval foo)

(use 'wizard)
(defn game-repl []
  (loop []
    (println (eval (read)))
    (recur)))
(game-repl)
(look)

(use 'game-repl)
(game-read)

(game-print '[THIS IS A SENTENCE. WHAT ABOUT THIS? PROBABLY.])
(game-print '[not only does this sentence have a "comma," it also mentions the "iPad."])

(game-repl)

(game-repl)
