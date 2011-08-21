#lang racket

;;Use hygienic macros
(define-syntax-rule (when test branch ...)
  (cond (test (begin branch ...))))
(define-syntax-rule (unless test branch ...)
  (cond ((not test) (begin branch ...))))
(define-syntax-rule (unless test branch ...)
  (when (not test)
    branch ...))

(define-syntax-rule (my-or x y)
  (if x
      x
      y))
(my-or 1 2)
(my-or #f 2)
(my-or (begin (display "doing first argument")
              (newline)
              #t)
       2)
(define-syntax-rule (my-or x y)
  (let ((temp x))
    (if temp
        temp
        y)))
(define temp 3)
(my-or #f temp) ;;Works fine, thanks to hygienic macros

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
