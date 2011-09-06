#lang racket

(provide (all-defined-out))

(require mzlib/defmacro)

(define-macro (coroutine x . body)
  (let ((local-control-state (gensym)))
    `(letrec ((,local-control-state (lambda (,x)
                                      ,@body))
              (resume (lambda (c v)
                        (call/cc
                         (lambda (k)
                           (set! ,local-control-state k)
                           (c v))))))
       (lambda (v)
         (,local-control-state v)))))
