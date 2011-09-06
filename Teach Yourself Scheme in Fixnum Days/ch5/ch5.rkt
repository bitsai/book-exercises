#lang racket

(define x 9)
(define (add2 x) (+ x 2))
x
(add2 3)
(add2 x)
x
(set! x 20)
(define (add2 x)
  (set! x (+ x 2))
  x)
(add2 x)
x
(define counter 0)
(define (bump-counter)
  (set! counter (add1 counter))
  counter)
(bump-counter)
(bump-counter)
(bump-counter)

(let ((x 1)
      (y 2)
      (z 3))
  (list x y z))
(let ((x 1)
      (y x))
  (+ x y))
(let* ((x 1)
       (y x))
  (+ x y))
(let ((x 1))
  (let ((y x))
    (+ x y)))
(let ((cons (lambda (x y) (+ x y))))
  (cons 1 2))

(require "fluid-let.rkt")
(fluid-let ((counter 99))
  (display (bump-counter)) (newline)
  (display (bump-counter)) (newline)
  (display (bump-counter)) (newline))
counter
(let ((counter 99))
  (display (bump-counter)) (newline)
  (display (bump-counter)) (newline)
  (display (bump-counter)) (newline))
counter
