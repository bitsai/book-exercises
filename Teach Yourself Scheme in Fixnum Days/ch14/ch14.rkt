#lang racket

(require "amb.rkt")
(init-amb-fail)
(amb 1 2)
(init-amb-fail)
(amb)
(init-amb-fail)
(amb 1 (amb))
(init-amb-fail)
(amb (amb) 1)
(init-amb-fail)
(if (amb #t #f)
    1
    (amb))

(init-amb-fail)
(amb 1 2 3 4 5 6 7 8 9 10)
(define (number-between lo hi)
  (let loop ((i lo))
    (if (> i hi)
        (amb)
        (amb i (loop (add1 i))))))
(define (assert pred)
  (unless pred
    (amb)))
(define (gen-even hi)
  (let ((i (number-between 2 hi)))
    (assert (even? i))
    i))
(init-amb-fail)
(gen-even 20)
(amb)
(amb)
(init-amb-fail)
(bag-of (gen-even 20))

(define (xor x y)
  (or (and x (not y))
      (and (not x) y)))
(define (solve-kalotan-puzzle)
  (let ((parent1 (amb 'm 'f))
        (parent2 (amb 'm 'f))
        (kibi (amb 'm 'f))
        (kibi-self-desc (amb 'm 'f))
        (kibi-lied? (amb #t #f)))
    (assert
     (not (eqv? parent1 parent2)))
    (assert
     (when (eqv? kibi 'm)
       (not kibi-lied?)))
    (assert
     (when kibi-lied?
       (xor (and (eqv? kibi-self-desc 'm)
                 (eqv? kibi 'f))
            (and (eqv? kibi-self-desc 'f)
                 (eqv? kibi 'm)))))
    (assert
     (when (not kibi-lied?)
       (xor (and (eqv? kibi-self-desc 'm)
                 (eqv? kibi 'm))
            (and (eqv? kibi-self-desc 'f)
                 (eqv? kibi 'f)))))
    (assert
     (when (eqv? parent1 'm)
       (and (eqv? kibi-self-desc 'm)
            (xor (and (eqv? kibi 'f)
                      (eqv? kibi-lied? #f))
                 (and (eqv? kibi 'm)
                      (eqv? kibi-lied? #t))))))
    (assert
     (when (eqv? parent1 'f)
       (and (eqv? kibi 'f)
            (eqv? kibi-lied? #t))))
    (list parent1 parent2 kibi kibi-self-desc kibi-lied?)))
(init-amb-fail)
(solve-kalotan-puzzle)

(define (choose-color)
  (amb 'red 'yellow 'blue 'white))
(define (color-europe)
  (let* ((p (choose-color))
         (e (choose-color))
         (f (choose-color))
         (b (choose-color))
         (h (choose-color))
         (g (choose-color))
         (l (choose-color))
         (i (choose-color))
         (s (choose-color))
         (a (choose-color))
         (portugal (list 'portugal p (list e)))
         (spain (list 'spain e (list f p)))
         (france (list 'france f (list e i s b g l)))
         (belgium (list 'belgium b (list f h l g)))
         (holland (list 'holland h (list b g)))
         (germany (list 'germany g (list f a s h b l)))
         (luxembourg (list 'luxembourg l (list f b g)))
         (italy (list 'italy i (list f a s)))
         (switzerland (list 'switzerland s (list f i a g)))
         (austria (list 'austria a (list i s g)))
         (countries (list portugal
                          spain
                          france
                          belgium
                          holland
                          germany
                          luxembourg
                          italy
                          switzerland
                          austria)))
    (for-each (lambda (c)
                (assert
                 (not (memq (cadr c)
                            (caddr c)))))
              countries)
    (for-each (lambda (c)
                (display (car c))
                (display " ")
                (display (cadr c))
                (newline))
              countries)))
(init-amb-fail)
(color-europe)
