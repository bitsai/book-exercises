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

(define (delete-duplicates lst)
  (if (null? lst)
      lst
      (let ((x (car lst))
            (xs (cdr lst)))
        (if (memv x xs)
            (delete-duplicates xs)
            (cons x (delete-duplicates xs))))))

(define (append-map f lst)
  (let loop ((lst lst))
    (if (null? lst)
        '()
        (append (f (car lst))
                (loop (cdr lst))))))

(define-macro (create-class direct-supers slots . methods)
  `(create-class-proc
    (list ,@(map (lambda (su) `,su) direct-supers))
    (list ,@(map (lambda (slot) `',slot) slots))
    (list ,@(map (lambda (method) `',(car method)) methods))
    (vector ,@(map (lambda (method) `,(cadr method)) methods))))

(define (make-instance class . slot-value-pairs)
  (let* ((slots (class-slots class))
         (n (length slots))
         (instance (make-vector (add1 n))))
    (vector-set! instance 0 class)
    (let loop ((slot-value-pairs slot-value-pairs))
      (if (null? slot-value-pairs)
          instance
          (let ((k (list-position (car slot-value-pairs) slots)))
            (vector-set! instance (add1 k) (cadr slot-value-pairs))
            (loop (cddr slot-value-pairs)))))))

(define standard-class (vector 'place-holder
                               (list 'slots
                                     'supers
                                     'method-names
                                     'method-vector)
                               '()
                               '(make-instance)
                               (vector make-instance)))

(vector-set! standard-class 0 standard-class)

(define (make-standard-class . args)
  (apply send 'make-instance standard-class args))

(define (standard-class? x)
  (and (vector? x)
       (>= (vector-length x) 1)
       (eqv? (vector-ref x 0) standard-class)))

(define (class-slots c)
  (vector-ref c 1))

(define (class-supers c)
  (vector-ref c 2))

(define (class-method-names c)
  (vector-ref c 3))

(define (class-method-vector c)
  (vector-ref c 4))

(define (class-of x)
  (if (vector? x)
      (let ((n (vector-length x)))
        (if (>= n 1)
            (let ((class (vector-ref x 0)))
              (if (standard-class? class)
                  class
                  #t))
            #t))
      #t))

(define (slot-value instance slot)
  (let* ((class (class-of instance))
         (k (list-position slot (class-slots class))))
    (if k
        (vector-ref instance (add1 k))
        #f)))

(define (set!slot-value instance slot value)
  (let* ((class (class-of instance))
         (k (list-position slot (class-slots class))))
    (if k
        (vector-set! instance (add1 k) value)
        #f)))

(define (create-class-proc direct-supers slots method-names method-vector)
  (let ((supers (delete-duplicates (append direct-supers
                                           (append-map (lambda (c)
                                                         (class-supers c))
                                                       direct-supers)))))
    (make-standard-class
     'supers supers
     'slots (delete-duplicates (append slots
                                       (append-map (lambda (c)
                                                     (class-slots c))
                                                   supers)))
     'method-names method-names
     'method-vector method-vector)))

(define (send method-name instance . args)
  (let ((proc (let ((class (class-of instance)))
                (if (eqv? #t class)
                    (error 'send)
                    (let loop ((class class)
                               (supers (class-supers class)))
                      (let* ((method-names (class-method-names class))
                             (k (list-position method-name method-names)))
                        (cond (k (vector-ref (class-method-vector class) k))
                              ((null? supers) (error 'send))
                              (else (loop (car supers)
                                          (cdr supers))))))))))
    (apply proc instance args)))
