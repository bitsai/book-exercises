#lang racket

(provide (all-defined-out))

(require mzlib/defmacro)

(define amb-fail '*)

(define (set-amb-fail f)
  (set! amb-fail f))

(define (init-amb-fail)
  (set-amb-fail
   (lambda ()
     (error "amb tree exhausted"))))

(define-macro (amb . alts)
  (let ((prev-amb-fail (gensym))
        (sk (gensym))
        (fk (gensym)))
    `(let ((,prev-amb-fail amb-fail))
       (call/cc
        (lambda (,sk)
          ,@(map (lambda (alt)
                   `(call/cc
                     (lambda (,fk)
                       (set-amb-fail
                        (lambda ()
                          (set-amb-fail ,prev-amb-fail)
                          (,fk 'fail)))
                       (,sk ,alt))))
                 alts)
          (,prev-amb-fail))))))

(define-macro (bag-of e)
  (let ((prev-amb-fail (gensym))
        (results (gensym))
        (k (gensym))
        (v (gensym)))
    `(let ((,prev-amb-fail amb-fail)
           (,results '()))
       (when (call/cc
              (lambda (,k)
                (set-amb-fail
                 (lambda ()
                   (,k #f)))
                (let ((,v ,e))
                  (set! ,results (cons ,v ,results))
                  (,k #t))))
         (amb-fail))
       (set-amb-fail ,prev-amb-fail)
       (reverse ,results))))
