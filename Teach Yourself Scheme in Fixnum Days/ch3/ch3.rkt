#lang racket

(lambda (x) (+ x 2))
((lambda (x) (+ x 2)) 5)
(define (add2 x) ;;Syntactic sugar for defining procedures
  (+ x 2))
(add2 4)
(add2 9)

(define (area length breadth)
  (* length breadth))
(define area *)

(define x '(1 2 3))
(apply + x)
(apply + 1 2 3 x)

(define (display3 arg1 arg2 arg3)
  (begin (display arg1)
         (display " ")
         (display arg2)
         (display " ")
         (display arg3)
         (newline)))
(define (display3 arg1 arg2 arg3)
  (display arg1)
  (display " ")
  (display arg2)
  (display " ")
  (display arg3)
  (newline))
