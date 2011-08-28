#lang racket

(require "table.rkt")
(define t (make-table))
(table? t)
(table? #())
(table-get t 0)
(table-put! t 0 0)
(table-get t 0)
(table-put! t 0 "a")
(table-put! t 1 "b")
(table-put! t 2 "c")
(table-for-each t
                (lambda (k v)
                  (display k)
                  (display " ")
                  (display v)
                  (newline)))
