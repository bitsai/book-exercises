(defn square [x]
  (* x x))
(defn sum-of-squares [x y]
  (+ (square x) (square y)))
(defn abs [x]
  (if (< x 0)
    (- x)
    x))

;; Exercise 1.1
10 ;; 10
(+ 5 3 4) ;; 12
(- 9 1) ;; 8
(/ 6 2) ;; 3
(+ (* 2 4) (- 4 6)) ;; 6
(def a 3) ;; #'user/a
(def b (+ a 1)) ;; #'user/b
(+ a b (* a b)) ;; 19
(= a b) ;; false
(if (and (> b a) (< b (* a b)))
  b
  a) ;; 4
(cond (= a 4) 6
      (= b 4) (+ 6 7 a)
      :else 25) ;; 16
(+ 2 (if (> b a) b a)) ;; 6
(* (cond (> a b) a
         (< a b) b
         :else -1)
   (+ a 1)) ;; 16

;; Exercise 1.2
(/ (+ 5 4 (- 2 (- 3 (+ 6 (/ 4 5))))) (* 3 (- 6 2) (- 2 7))) ;; -37/150

;; Exercise 1.3
(defn f [x y z]
  (cond (and (<= x y) (<= x z)) (sum-of-squares y z)
        (and (<= y x) (<= y z)) (sum-of-squares x z)
        :else (sum-of-squares x y)))

;; Exercise 1.4
;; If b is positive, add a and b, otherwise subtract b from a.
;; More simply, add a and the absolute value of b.

;; Exercise 1.5
(defn p [] (p))
(defn test [x y]
  (if (= x 0)
    0
    y))
(test 0 (p))
;; With applicative-order (Scheme, Clojure), Ben will observe infinite
;; recursion/stack overflow, because p is evaluated right away.
;; With normal-order (Haskell), Ben will observe 0, because test will
;; succeed and return 0 before p is ever evaluated.

(defn average [x y]
  (/ (+ x y) 2))
(defn improve [guess x]
  (average guess (/ x guess)))
(defn good-enough? [guess x]
  (< (abs (- (square guess) x)) 0.001))
(defn sqrt-iter [guess x]
  (if (good-enough? guess x)
    guess
    (recur (improve guess x)
           x)))
(defn sqrt [x]
  (sqrt-iter 1.0 x))

;; Exercise 1.6
(defn new-if [pred then-clause else-clause]
  (cond pred then-clause
        :else else-clause))
(defn sqrt-iter [guess x]
  (new-if (good-enough? guess x)
          guess
          (recur (improve guess x)
                 x)))
;; Alyssa will observe infinite recursion/stack overflow, because new-if is a
;; normal function, so then-clause and else-clause are evaluated right away,
;; unlike the conditional evaluation of the 'if' and 'cond' special forms.

;; Exercise 1.7
;; For very small numbers, the threashold in good-enough? is reached too
;; soon, and good-enough? returns true before guess is improved much.
;; ex. (sqrt 0.0000000000000000001) -> 0.03125
;; For very large numbers, limited precision means a point is reached where
;; guess does not change, resulting in infinite recursion/stack overflow.
;; ex. (sqrt 1000000000000000000000) -> StackOverflowError
(defn good-enough? [guess prev-guess]
  (if prev-guess
    (< (abs (- guess prev-guess)) (* guess 0.001))
    false))
(defn sqrt-iter [guess prev-guess x]
  (if (good-enough? guess prev-guess)
    guess
    (recur (improve guess x)
           guess
           x)))
(defn sqrt [x]
  (sqrt-iter 1.0 nil x))

;; Exercise 1.8
(defn improve-cbrt [guess x]
  (/ (+ (/ x (square guess)) (* 2 guess)) 3))
(defn cbrt-iter [guess prev-guess x]
  (if (good-enough? guess prev-guess)
    guess
    (recur (improve-cbrt guess x)
           guess
           x)))
(defn cbrt [x]
  (cbrt-iter 1.0 nil x))
