#lang racket

;;Use Racket structs
(struct tree (height girth age leaf-shape leaf-color) #:mutable)
(define coconut (tree 30 #f 5 'frond #f)) ;;All fields required
(tree-height coconut)
(tree-leaf-shape coconut)
(tree-girth coconut)
(set-tree-height! coconut 40)
(set-tree-girth! coconut 10)
(tree-height coconut)
(tree-girth coconut)

;;Racket structs don't do default initializations
(define palm (tree 60 #f #f 'frond 'green))
(tree-height palm)
(tree-leaf-shape palm)
(define plantain (tree 7 #f #f 'sheet 'green))
(tree-height plantain)
(tree-leaf-shape plantain)
(tree-leaf-color plantain)
