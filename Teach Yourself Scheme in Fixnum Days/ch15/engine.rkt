#lang racket

(provide (all-defined-out))

(require "clock.rkt")
(require "fluid-let.rkt")

(define *engine-escape* #f)
(define *engine-entrance* #f)

(clock 'set-handler (lambda () (call/cc *engine-escape*)))

(define (make-engine th)
  (lambda (ticks success failure)
    (let* ((parent-ticks (clock 'set +inf.0))
           (child-available-ticks (min parent-ticks ticks))
           (parent-ticks-left (- parent-ticks child-available-ticks))
           (child-ticks-left (- ticks child-available-ticks))
           (ticks-left 0)
           (engine-succeeded? #f)
           (result (fluid-let ((*engine-escape* #f)
                               (*engine-entrance* #f))
                     (call/cc
                      (lambda (k)
                        (set! *engine-escape* k)
                        (let ((result (call/cc
                                       (lambda (k)
                                         (set! *engine-entrance* k)
                                         (clock 'set child-available-ticks)
                                         (let ((v (th)))
                                           (*engine-entrance* v))))))
                          (set! ticks-left
                                (let ((n (clock 'set +inf.0)))
                                  (if (eqv? n +inf.0)
                                      0
                                      n)))
                          (set! engine-succeeded? #t)
                          result))))))
      (set! parent-ticks-left (+ parent-ticks-left ticks-left))
      (set! ticks-left (+ ticks-left child-ticks-left))
      (clock 'set parent-ticks-left)
      (cond (engine-succeeded? (success result ticks-left))
            ((zero? ticks-left) (failure (make-engine (lambda ()
                                                        (result 'resume)))))
            (else ((make-engine (lambda () (result 'resume)))
                   ticks-left success failure))))))
