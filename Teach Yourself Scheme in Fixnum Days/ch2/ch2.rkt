#lang racket

(boolean? #t)
(boolean? "Hello, World!")
(not #f)
(not #t)
(not "Hello, World!")

(number? 42)
(number? #t)
(complex? 2+3i)
(real? 2+3i)
(real? 3.1416)
(real? 22/7)
(real? 42)
(rational? 2+3i)
(rational? 3.1416)
(rational? 22/7)
(integer? 22/7)
(integer? 42)
(eqv? 42 42)
(eqv? 42 #f)
(eqv? 42 42.0)
(= 42 42)
(= 42 #f)
(= 42 42.0)
(< 3 2)
(>= 4.5 3)
(+ 1 2 3)
(- 5.3 2)
(- 5 2 1)
(* 1 2 3)
(/ 6 3)
(/ 22 7)
(expt 2 3)
(expt 4 1/2)
(- 4)
(/ 4)
(max 1 3 4 2 3)
(min 1 3 4 2 3)
(abs 3)
(abs -4)

(char? #\c)
(char? 1)
(char? #\;)
(char=? #\a #\a)
(char<? #\a #\b)
(char>=? #\a #\b)
(char-ci=? #\a #\A)
(char-ci<? #\a #\B)
(char-downcase #\A)
(char-upcase #\a)

#t
42
#\c
(quote xyz)
'E
(symbol? 'xyz)
(symbol? 42)
(eqv? 'Calorie 'calorie) ;;Racket is case-sensitive
(define xyz 9)
xyz
(set! xyz #\c)
xyz

"Hello, World!"
(string #\h #\e #\l #\l #\o)
(define greeting "Hello; Hello!")
(string-ref greeting 0)
(string-append "E "
               "Pluribus "
               "Unum")
(define a-3-char-long-string (make-string 3))
(define hello (string #\h #\e #\l #\l #\o))
hello
(string-set! hello 1 #\a)
hello

(vector 0 1 2 3 4)
(define v (make-vector 5))

(cons 1 #t)
'(1 . #t)
(1 . #t)
(define x (mcons 1 #t)) ;;Racket uses mcons for mutable dotted pairs
(mcar x) ;;Racket uses mcar and mcdr for mutable dotted pairs
(mcdr x)
(set-mcar! x 2) ;;Racket uses set-mcar! and set-mcdr!
(set-mcdr! x #f)
x
(define y (cons (cons 1 2) 3))
y
(car (car y))
(cdr (car y))
(caar y)
(cdar y)
(cons 1 (cons 2 (cons 3 (cons 4 5))))
'()
'(1 . (2 . (3 . (4 . ()))))
(cons 1 (cons 2 (cons 3 (cons 4 '()))))
(list 1 2 3 4)
'(1 2 3 4)
(define y (list 1 2 3 4))
(list-ref y 0)
(list-ref y 3)
(list-tail y 1)
(list-tail y 3)
(pair? '(1 . 2))
(pair? '(1 2))
(pair? '())
(list? '())
(null? '())
(list? '(1 2))
(list? '(1 . 2))
(null? '(1 2))
(null? '(1 . 2))

(char->integer #\d)
(integer->char 50)
(string->list "hello")
(number->string 16)
(string->number "16")
(string->number "Am I a hot number?")
(string->number "16" 8)
(symbol->string 'symbol)
(string->symbol "string")

cons
(display "Hello, World!" (current-output-port))
