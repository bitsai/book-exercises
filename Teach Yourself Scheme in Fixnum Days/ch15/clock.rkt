#lang racket

(provide (all-defined-out))

(define clock-ticks +inf.0)
(define clock-handler (lambda () (when #f #f)))

(define (clock op . args)
  (case op
    ((set-handler) (set! clock-handler (car args)))
    ((set) (let ((new-ticks (car args))
                 (prev-ticks clock-ticks))
             (if (positive? new-ticks)
                 (set! clock-ticks new-ticks)
                 (begin (set! clock-ticks +inf.0)
                        (clock-handler)))
             prev-ticks))
    ((decrement) (begin (set! clock-ticks (sub1 clock-ticks))
                        (when (zero? clock-ticks)
                          (set! clock-ticks +inf.0)
                          (clock-handler))))))
