#!/Applications/Racket/bin/racket

#lang racket

(require "cgi.rkt")

(define uhoh #f)

(define (calc-eval e)
  (if (pair? e)
      (apply (ensure-operator (car e))
             (map calc-eval (cdr e)))
      (ensure-number e)))

(define (ensure-operator e)
  (case e
    ((+) +)
    ((-) -)
    ((*) *)
    ((/) /)
    ((**) expt)
    (else (uhoh "unpermitted operator"))))

(define (ensure-number e)
  (if (number? e)
      e
      (uhoh "non-number")))

(define (print-form)
  (display "<form action=\"")
  (display (getenv "SCRIPT_NAME"))
  (display "\">
Enter Arithmetic expression:<br>
<input type=textarea name=arith-exp><p>
<input type=submit value=\"Evaluate\">
<input type=reset value=\"Clear\">
</form>"))

(define (print-page-begin)
  (display "content-type: text/html

<html>
<head>
<title>A Racket Calculator</title>
</head>
<body>"))

(define (print-page-end)
  (display "</body>
</html>"))

(parse-form-data)

(print-page-begin)

(let ((e (form-data-get "arith-exp")))
  (unless (null? e)
    (let ((e1 (car e)))
      (display-html e1)
      (display "<p>
=&gt;&nbsp;&nbsp;")
      (display-html
       (call/cc (lambda (k)
                  (set! uhoh (lambda (s)
                               (k (string-append "Error: " s))))
                  (number->string
                   (calc-eval (read (open-input-string e1)))))))
      (display "<p>"))))

(print-form)

(print-page-end)
