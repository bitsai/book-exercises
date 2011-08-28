#lang racket

(require "object.rkt")
(define trivial-bike-class (make-standard-class
                            'supers '()
                            'slots '(frame size parts)
                            'method-names '()
                            'method-vector #()))
(define my-bike (make-instance trivial-bike-class
                               'frame 'cromoly
                               'size '18.5
                               'parts 'alivio))
my-bike
(class-of my-bike)
(slot-value my-bike 'frame)
(set!slot-value my-bike 'frame 'unobtanium)
(slot-value my-bike 'frame)
(define bike-class (create-class
                    ()
                    (frame size parts chain tires)
                    (check-fit (lambda (me inseam)
                                 (let* ((bike-size (slot-value me 'size))
                                        (ideal-size (* inseam 3/5))
                                        (diff (- bike-size ideal-size)))
                                   (cond ((<= -1 diff 1) 'perfect-fit)
                                         ((<= -2 diff 2) 'fits-well)
                                         ((< diff -2) 'too-small)
                                         ((> diff 2) 'too-big)))))))
(define my-bike (make-instance bike-class
                               'frame 'titanium
                               'size 21
                               'parts 'ultegra
                               'chain 'sachs
                               'tires 'continental))
(send 'check-fit my-bike 34)
(class-of my-bike)
(slot-value my-bike 'suspension)
(set!slot-value my-bike 'suspension 'awesome)
(slot-value my-bike 'suspension)
(define mtn-bike-class (create-class
                        (bike-class)
                        (suspension)
                        (check-fit (lambda (me inseam)
                                     (let* ((bike-size (slot-value me 'size))
                                            (ideal-size (- (* inseam 3/5) 2))
                                            (diff (- bike-size ideal-size)))
                                       (cond ((<= -2 diff 2) 'perfect-fit)
                                             ((<= -4 diff 4) 'fits-well)
                                             ((< diff -4) 'too-small)
                                             ((> diff 4) 'too-big)))))))
(define my-bike (make-instance mtn-bike-class
                               'frame 'titanium
                               'size 21
                               'parts 'ultegra
                               'chain 'sachs
                               'tires 'continental
                               'suspension 'none))
(send 'check-fit my-bike 34)
(class-of my-bike)
(slot-value my-bike 'suspension)
(set!slot-value my-bike 'suspension 'awesome)
(slot-value my-bike 'suspension)
