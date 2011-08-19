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
  (set! counter (+ counter 1))
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

;;Borrowed from: http://www.scheme.com/csug8/binding.html
(define-syntax fluid-let
  (lambda (x)
    (syntax-case x ()
      [(_ () b1 b2 ...) #'(let () b1 b2 ...)]
      [(_ ((x e) ...) b1 b2 ...)
       (andmap identifier? (syntax-e #'(x ...))) ;;Added syntax-e
       (with-syntax ([(y ...) (generate-temporaries #'(x ...))])
         #'(let ([y e] ...)
             (let ([swap (lambda ()
                           (let ([t x])
                             (set! x y)
                             (set! y t))
                           ...)])
               (dynamic-wind swap (lambda () b1 b2 ...) swap))))])))
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
