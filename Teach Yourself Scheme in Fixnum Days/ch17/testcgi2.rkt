#!/Applications/Racket/bin/racket

#lang racket

(require "cgi.rkt")

(display "content-type: text/plain")
(newline)
(newline)

(parse-form-data)

(let ((env-var (form-data-get/1 "env-var")))
  (display env-var)
  (display " = ")
  (display (or (getenv env-var) ""))
  (newline))
