(throw (Exception. "foo"))

(def foo (Exception. "Stop FOOing around, numbskull!"))
(throw foo)

(defn bad-function []
  (throw foo))
(try
  (bad-function)
  (catch Exception e
    (println "somebody threw an Exception!"))
  (catch ClassNotFoundException e
    (println "somebody threw a ClassNotFoundException!")))

(try
  (/ 1 0)
  (finally (println "I need to say 'flubyduby' no matter what")))

(use 'server)
(decode-param "foo")
(decode-param "foo%3F")
(decode-param "foo+bar")

(parse-params "name=bob&age=25&gender=male")

(parse-url "GET /lolcats.html HTTP/1.1")
(parse-url "GET /lolcats.html?extra-funny=yes HTTP/1.1")

(use 'clojure.java.io)
(get-header (reader (char-array "foo: 1\nbar: abc, 123\n\n")))

(hello-request-handler "lolcats" nil nil)
(hello-request-handler "greeting" nil nil)
(hello-request-handler "greeting" nil {"NAME" "Bob"})
