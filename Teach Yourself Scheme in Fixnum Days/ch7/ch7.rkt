#lang racket

(display 9)
(display 9 (current-output-port))
(define i (open-input-file "hello.txt"))
(read-char i)
(define j (read i))
j
(define o (open-output-file "greeting.txt"))
(display "hello" o)
(write-char #\space o)
(display 'world o)
(newline o)
(close-output-port o)

(call-with-input-file "hello.txt"
  (lambda (i)
    (let* ((a (read-char i))
           (b (read-char i))
           (c (read-char i)))
      (list a b c))))

(define i (open-input-string "hello world"))
(read-char i)
(read i)
(read i)
(define o (open-output-string))
(write 'hello o)
(write-char #\, o)
(display " " o)
(display "world" o)
(get-output-string o)
