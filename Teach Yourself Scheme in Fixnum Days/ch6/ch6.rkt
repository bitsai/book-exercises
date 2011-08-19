#lang racket

(define (factorial n)
  (if (= n 0)
      1
      (* n (factorial (- n 1)))))
(define (is-even? n)
  (if (= n 0)
      #t
      (is-odd? (- n 1))))
(define (is-odd? n)
  (if (= n 0)
      #f
      (is-even? (- n 1))))

(let ((local-even? (lambda (n)
                     (if (= n 0)
                         #t
                         (local-odd? (- n 1)))))
      (local-odd? (lambda (n)
                    (if (= n 0)
                        #f
                        (local-even? (- n 1))))))
  (list (local-even? 23) (local-odd? 23)))
(letrec ((local-even? (lambda (n)
                        (if (= n 0)
                            #t
                            (local-odd? (- n 1)))))
         (local-odd? (lambda (n)
                       (if (= n 0)
                           #f
                           (local-even? (- n 1))))))
  (list (local-even? 23) (local-odd? 23)))

(letrec ((countdown (lambda (i)
                      (if (= i 0)
                          'liftoff
                          (begin (display i)
                                 (newline)
                                 (countdown (- i 1)))))))
  (countdown 10))
(let countdown ((i 10))
  (if (= i 0)
      'liftoff
      (begin (display i)
             (newline)
             (countdown (- i 1)))))

(define (list-position o l)
  (let loop ((i 0)
             (l l))
    (cond ((null? l) #f)
          ((eqv? o (car l)) i)
          (else (loop (+ i 1)
                      (cdr l))))))
(define (reverse! s)
  (let loop ((s s)
             (r '()))
    (if (null? s)
        r
        (let ((d (mcdr s)))
          (set-mcdr! s r)
          (loop d
                s)))))

(define (add2 x)
  (+ x 2))
(map add2 '(1 2 3))
(for-each display '("one " "two " "buckle my shoe"))
(map cons '(1 2 3) '(10 20 30))
(map + '(1 2 3) '(10 20 30))
