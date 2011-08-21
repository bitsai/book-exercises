#lang racket

(file-or-directory-modify-seconds "hello.rkt")

(system "ls")
(define fname "spot")
(system (string-append "test -f " fname))
(system (string-append "rm -rf " fname))
(file-exists? fname)
(delete-file fname)

(getenv "HOME")
(getenv "SHELL")
