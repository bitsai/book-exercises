#lang racket

;;Racket has hash tables, but we'll make a custom table anyway
(struct table (equ alist) #:mutable)
(define (table-get tbl k . d)
  (let ((c (lassoc k (table-alist tbl) (table-equ tbl))))
    (cond (c (mcdr c)) ;;Use mcdr
          ((pair? d) (car d)))))
(define (lassoc k al equ?)
  (let loop ((al al))
    (if (null? al)
        #f
        (let ((c (car al)))
          (if (equ? (mcar c) k) ;;Use mcar
              c
              (loop (cdr al)))))))
(define (table-put! tbl k v)
  (let* ((al (table-alist tbl))
         (c (lassoc k al (table-equ tbl))))
    (if c
        (set-mcdr! c v) ;;Use set-mcdr!
        (set-table-alist! tbl (cons (mcons k v) al))))) ;;Use mcons
(define (table-foreach tbl p)
  (for-each
   (lambda (c)
     (p (mcar c) (mcdr c))) ;;Use mcar & mcdr
   (table-alist tbl)))
