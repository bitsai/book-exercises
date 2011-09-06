#lang racket

(provide (all-defined-out))

(require mzlib/defmacro)

(define (list-position x lst)
  (let loop ((i 0)
             (lst lst))
    (cond ((null? lst) #f)
          ((eqv? x (car lst)) i)
          (else (loop (add1 i)
                      (cdr lst))))))

(define-macro (defstruct s . ff)
  (let* ((s-s (symbol->string s))
         (n (length ff))
         (n+1 (add1 n))
         (vv (make-vector n+1)))
    (let loop ((i 1)
               (ff ff))
      (when (<= i n)
        (let ((f (car ff)))
          (vector-set! vv i (if (pair? f) (cadr f) '(when #f #f)))
          (loop (add1 i)
                (cdr ff)))))
    (let ((ff (map (lambda (f) (if (pair? f) (car f) f)) ff)))
      `(begin
         (define (,(string->symbol (string-append "make-" s-s)) . fvfv)
           (let ((st (make-vector ,n+1))
                 (ff ',ff))
             (vector-set! st 0 ',s)
             ,@(let loop ((i 1)
                          (r '()))
                 (if (>= i n+1)
                     r
                     (loop (add1 i)
                           (cons `(vector-set! st ,i ,(vector-ref vv i))
                                 r))))
             (let loop ((fvfv fvfv))
               (unless (null? fvfv)
                 (vector-set! st
                              (add1 (list-position (car fvfv) ff))
                              (cadr fvfv))
                 (loop (cddr fvfv))))
             st))
         ,@(let loop ((i 1)
                      (procs '()))
             (if (>= i n+1)
                 procs
                 (loop (add1 i)
                       (let ((f (symbol->string (list-ref ff (sub1 i)))))
                         (cons
                          `(define (,(string->symbol
                                      (string-append
                                       s-s "." f)) x)
                             (vector-ref x ,i))
                          (cons
                           `(define (,(string->symbol
                                       (string-append
                                        "set!" s-s "." f)) x v)
                              (vector-set! x ,i v))
                           procs))))))
         (define (,(string->symbol (string-append s-s "?")) x)
           (and (vector? x)
                (>= (vector-length x) 1)
                (eqv? (vector-ref x 0) ',s)))))))
