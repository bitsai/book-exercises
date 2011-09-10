#!/Applications/Racket/bin/racket

#lang racket

(display "content-type: text/plain")
(newline)
(newline)

(for-each (lambda (env-var)
            (display env-var)
            (display " = ")
            (display (or (getenv env-var) ""))
            (newline))
          '("AUTH_TYPE"
            "CONTENT_LENGTH"
            "CONTENT_TYPE"
            "DOCUMENT_ROOT"
            "GATEWAY_INTERFACE"
            "HTTP_ACCEPT"
            "HTTP_REFERER"
            "HTTP_USER_AGENT"
            "PATH_INFO"
            "PATH_TRANSLATED"
            "QUERY_STRING"
            "REMOTE_ADDR"
            "REMOTE_HOST"
            "REMOTE_IDENT"
            "REMOTE_USER"
            "REQUEST_METHOD"
            "SCRIPT_NAME"
            "SERVER_NAME"
            "SERVER_PORT"
            "SERVER_PROTOCOL"
            "SERVER_SOFTWARE"))
