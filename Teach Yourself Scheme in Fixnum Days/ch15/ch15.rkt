#lang racket

(require "clock.rkt")
(require "engine.rkt")
(define printn-engine
  (make-engine
   (lambda ()
     (let loop ((i 0))
       (display i)
       (newline)
       (clock 'decrement)
       (loop (add1 i))))))
(define *more* #f)
(printn-engine 10 list (lambda (ne) (set! *more* ne)))
(*more* 10 list (lambda (ne) (set! *more* ne)))

(clock 'set-handler (lambda () (error "Say goodnight, cat!")))
(clock 'set 9)
(for-each (lambda (_) (clock 'decrement))
          '(0 1 2 3 4 5 6 7 8))

(clock 'set-handler (lambda () (call/cc *engine-escape*)))
(define *parent-more* #f)
(define parent-engine
  (make-engine
   (lambda ()
     (printn-engine 10 list (lambda (ne) (set! *more* ne))))))
(define *grand-parent-more* #f)
(define grand-parent-engine
  (make-engine
   (lambda ()
     (parent-engine 14 list (lambda (ne) (set! *parent-more* ne))))))
(grand-parent-engine 6 list (lambda (ne) (set! *grand-parent-more* ne)))
(*grand-parent-more* 6 list (lambda (ne) (set! *grand-parent-more* ne)))
