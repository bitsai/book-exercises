;; Clojure doesn't have output-stream predicate
(print \X)

;; Clojure doesn't have input-stream predicate
;; Clojure doesn't have read-char
(read)

(spit "data.txt" "my data")
(slurp "data.txt")
(let [animal-noises '{dog woof
                      cat meow}]
  (spit "animal-noises.txt" animal-noises))
(read-string (slurp "animal-noises.txt"))
;; spit will happily overwrite existing file
(spit "data.txt" "my data")

;; Server code
(import '[java.net ServerSocket])
(def server (ServerSocket. 4321))
(def socket (.accept server))
(def in-stream (.getInputStream socket))
(def out-stream (.getOutputStream socket))
(let [len (.available in-stream)
      bytes (byte-array len)]
  (.read in-stream bytes)
  (String. bytes 0 len))
(.write out-stream (.getBytes "What up, Client!"))
(.close out-stream)
(.close in-stream)
(.close socket)
(.close server)

;; Client code
(import '[java.net Socket])
(def socket (Socket. "127.0.0.1" 4321))
(def in-stream (.getInputStream socket))
(def out-stream (.getOutputStream socket))
(.write out-stream (.getBytes "Yo Server!"))
(let [len (.available in-stream)
      bytes (byte-array len)]
  (.read in-stream bytes)
  (String. bytes 0 len))
(.close out-stream)
(.close in-stream)
(.close socket)

(let [foo (with-out-str
	    (print "This will go into foo. ")
	    (print "This will also go into foo."))]
  foo)

(with-out-str
  (print "the sum of" 5 "and" 2 "is" (+ 2 5)))
