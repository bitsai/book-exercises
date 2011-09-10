#lang racket

(provide (all-defined-out))

(require "table.rkt")

(define *form-data-table* (make-table 'equ string=?))

(define (parse-form-data)
  (if (string-ci=? (or (getenv "REQUEST_METHOD") "GET") "GET")
      (parse-form-data-using-query-string)
      (parse-form-data-using-stdin)))

(define (parse-form-data-using-query-string)
  (let ((query-string (or (getenv "QUERY_STRING") "")))
    (for-each (lambda (par-arg-str)
                (let* ((par-arg (split #\= par-arg-str))
                       (par (url-decode (car par-arg)))
                       (arg (if (> (length par-arg) 1)
                                (url-decode (cadr par-arg))
                                "")))
                  (table-put! *form-data-table*
                              par
                              (cons arg (form-data-get par)))))
              (split #\& query-string))))

(define (string-index s c)
  (let ((n (string-length s)))
    (let loop ((i 0))
      (cond ((>= i n) #f)
            ((char=? c (string-ref s i)) i)
            (else (loop (add1 i)))))))

(define (split c s)
  (let loop ((s s))
    (if (string=? "" s)
        '()
        (let ((i (string-index s c)))
          (if i
              (cons (substring s 0 i)
                    (loop (substring s (add1 i) (string-length s))))
              (list s))))))

(define (url-decode s)
  (list->string (let loop ((s (string->list s)))
                  (if (null? s)
                      '()
                      (let ((x (car s))
                            (xs (cdr s)))
                        (case x
                          ((#\+) (cons #\space (loop xs)))
                          ((#\%) (cons (hex->char (car xs) (cadr xs))
                                       (loop (cddr xs))))
                          (else (cons x (loop xs)))))))))

(define (hex->char x y)
  (integer->char (string->number (string x y) 16)))

(define (parse-form-data-using-stdin)
  (let* ((content-length-str (getenv "CONTENT_LENGTH"))
         (content-length (if content-length-str
                             (string->number content-length-str)
                             0))
         (i 0))
    (let par-loop ((par '()))
      (let ((c (read-char)))
        (set! i (add1 i))
        (if (or (> i content-length)
                (eof-object? c)
                (char=? c #\=))
            (let arg-loop ((arg '()))
              (let ((c (read-char)))
                (set! i (add1 i))
                (if (or (> i content-length)
                        (eof-object? c)
                        (char=? c #\&))
                    (let ((par (url-decode (list->string (reverse par))))
                          (arg (url-decode (list->string (reverse arg)))))
                      (table-put! *form-data-table*
                                  par
                                  (cons arg (form-data-get par)))
                      (unless (or (> i content-length)
                                  (eof-object? c))
                        (par-loop '())))
                    (arg-loop (cons c arg)))))
            (par-loop (cons c par)))))))

(define (form-data-get k)
  (table-get *form-data-table* k '()))

(define (form-data-get/1 k . default)
  (let ((vv (form-data-get k)))
    (cond ((pair? vv) (car vv))
          ((pair? default) (car default))
          (else ""))))

(define (display-html s . o)
  (let ((o (if (null? o)
               (current-output-port)
               (car o)))
        (n (string-length s)))
    (let loop ((i 0))
      (unless (>= i n)
        (let ((c (string-ref s i)))
          (display (case c
                     ((#\<) "&lt;")
                     ((#\>) "&gt;")
                     ((#\") "&quot;")
                     ((#\&) "&amp;")
                     (else c))
                   o)
          (loop (add1 i)))))))
