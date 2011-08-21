#lang racket

(provide defstruct list-position)

(require (lib "defmacro.ss"))

(define-macro (defstruct s . ff)
  (let* ((s-s (symbol->string s))
         (n (length ff))
         (n+1 (+ n 1))
         (vv (make-vector n+1)))
    (let loop ((i 1)
               (ff ff))
      (when (<= i n)
        (let ((f (car ff)))
          (vector-set! vv i (if (pair? f) (cadr f) '(cond (#f #f))))
          (loop (+ i 1)
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
                     (loop (+ i 1)
                           (cons `(vector-set! st ,i ,(vector-ref vv i))
                                 r))))
             (let loop ((fvfv fvfv))
               (unless (null? fvfv)
                 (vector-set! st
                              (+ (list-position (car fvfv) ff) 1)
                              (cadr fvfv))
                 (loop (cddr fvfv))))
             st))
         ,@(let loop ((i 1)
                      (procs '()))
             (if (>= i n+1)
                 procs
                 (loop (+ i 1)
                       (let ((f (symbol->string (list-ref ff (- i 1)))))
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
                (eqv? (vector-ref x 0) ',s)))))))

(define (list-position x lst)
  (let loop ((i 0)
             (lst lst))
    (if (null? lst)
        #f
        (if (eqv? x (car lst))
            i
            (loop (+ i 1)
                  (cdr lst))))))
