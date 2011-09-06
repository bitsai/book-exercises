#lang racket

(define (factorial n)
  (if (zero? n)
      1
      (* n (factorial (sub1 n)))))
(define (is-even? n)
  (if (zero? n)
      #t
      (is-odd? (sub1 n))))
(define (is-odd? n)
  (if (zero? n)
      #f
      (is-even? (sub1 n))))

(let ((local-even? (lambda (n)
                     (if (zero? n)
                         #t
                         (local-odd? (sub1 n)))))
      (local-odd? (lambda (n)
                    (if (zero? n)
                        #f
                        (local-even? (sub1 n))))))
  (list (local-even? 23) (local-odd? 23)))
(letrec ((local-even? (lambda (n)
                        (if (zero? n)
                            #t
                            (local-odd? (sub1 n)))))
         (local-odd? (lambda (n)
                       (if (zero? n)
                           #f
                           (local-even? (sub1 n))))))
  (list (local-even? 23) (local-odd? 23)))

(letrec ((countdown (lambda (i)
                      (if (zero? i)
                          'liftoff
                          (begin (display i)
                                 (newline)
                                 (countdown (sub1 i)))))))
  (countdown 10))
(let countdown ((i 10))
  (if (zero? i)
      'liftoff
      (begin (display i)
             (newline)
             (countdown (sub1 i)))))

(define (list-position o l)
  (let loop ((i 0)
             (l l))
    (cond ((null? l) #f)
          ((eqv? o (car l)) i)
          (else (loop (add1 i)
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
