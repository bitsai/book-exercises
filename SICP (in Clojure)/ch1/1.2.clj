(defn square [x]
  (* x x))
(defn abs [x]
  (if (< x 0)
    (- x)
    x))
(defn factorial [n]
  (if (= n 1)
    1
    (* n (factorial (- n 1)))))
(defn fact-iter [product counter max-count]
  (if (> counter max-count)
    product
    (recur (* counter product)
           (+ counter 1)
           max-count)))
(defn factorial [n]
  (fact-iter 1 1 n))

;; Exercise 1.9
;; (+ 4 5)
;; (inc (+ 3 5))
;; (inc (inc (+ 2 5)))
;; (inc (inc (inc (+ 1 5))))
;; (inc (inc (inc (inc (+ 0 5)))))
;; (inc (inc (inc (inc 5))))
;; (inc (inc (inc 6)))
;; (inc (inc 7))
;; (inc 8)
;; 9 -> recursive
;; (+ 4 5)
;; (+ 3 6)
;; (+ 2 7)
;; (+ 1 8)
;; (+ 0 9)
;; 9 -> iterative

;; Exercise 1.10
(defn A [x y]
  (cond (= y 0) 0
        (= x 0) (* 2 y)
        (= y 1) 2
        :else (recur (- x 1)
                     (A x (- y 1)))))
(A 1 10) ;; 1024
(A 2 4) ;; 65536
(A 3 3) ;; 65536
(defn f [n] (A 0 n)) ;; f(n) = 2n
(defn g [n] (A 1 n)) ;; g(n) = 2^n
(defn h [n] (A 2 n)) ;; h(n) = 2 exponentiated by itself n times (Tetration)

(defn fib [n]
  (cond (= n 0) 0
        (= n 1) 1
        :else (+ (fib (- n 1))
                 (fib (- n 2)))))
(defn fib-iter [a b count]
  (if (= count 0)
    b
    (recur (+ a b) a (- count 1))))
(defn fib [n]
  (fib-iter 1 0 n))
(defn first-denomination [kinds-of-coins]
  (cond (= kinds-of-coins 1) 1
        (= kinds-of-coins 2) 5
        (= kinds-of-coins 3) 10
        (= kinds-of-coins 4) 25
        (= kinds-of-coins 5) 50))
(defn cc [amount kinds-of-coins]
  (cond (= amount 0) 1
        (or (< amount 0) (= kinds-of-coins 0)) 0
        :else (+ (cc amount
                     (- kinds-of-coins 1))
                 (cc (- amount
                        (first-denomination kinds-of-coins))
                     kinds-of-coins))))
(defn count-change [amount]
  (cc amount 5))

;; Exercise 1.11
(defn g [a b c] (+ a (* 2 b) (* 3 c)))
(defn f [n]
  (if (< n 3)
    n
    (g (f (- n 1)) (f (- n 2)) (f (- n 3)))))
(defn f [n]
  (let [f-iter (fn [x a b c]
                 (if (= x n)
                   (g a b c)
                   (recur (+ x 1)
                          (g a b c)
                          a
                          b)))]
    (if (< n 3)
      n
      (f-iter 3 2 1 0))))

;; Exercise 1.12
(defn pascal-triangle [row col]
  (cond (or (< col 0) (> col row)) 0
        (and (= row 0) (= col 0)) 1
        :else (+ (pascal-triangle (- row 1) col)
                 (pascal-triangle (- row 1) (- col 1)))))

;; Exercise 1.13
;; ???

;; Exercise 1.14
;; http://www.billthelizard.com/2009/12/sicp-exercise-114-counting-change.html
;; Space: like fib, the max depth of cc's tree is proportional to amount = O(n)
;; Time: ???

;; Exercise 1.15
(defn cube [x]
  (* x x x))
(defn p [x]
  (- (* 3 x) (* 4 (cube x))))
(defn sine [angle]
  (if (not (> (abs angle) 0.1))
    angle
    (p (sine (/ angle 3.0)))))
;; a. 5
;; b. Since angle shrinks to 1/3 at each step, space and time = O(log n)

(defn expt [b n]
  (if (= n 0)
    1
    (* b (expt b (- n 1)))))
(defn expt-iter [b counter product]
  (if (= counter 0)
    product
    (recur b
           (- counter 1)
           (* b product))))
(defn expt [b n]
  (expt-iter b n 1))
(defn fast-expt [b n]
  (cond (= n 0) 1
        (even? n) (square (fast-expt b (/ n 2)))
        :else (* b (fast-expt b (- n 1)))))

;; Exercise 1.16
(defn expt-iter [b n acc]
  (cond (= n 0) acc
        (even? n) (recur (square b)
                         (/ n 2)
                         acc)
        :else (recur b
                     (- n 1)
                     (* b acc))))
(defn expt [b n]
  (expt-iter b n 1))

;; Exercise 1.17
(defn double [n]
  (* 2 n))
(defn halve [n]
  (/ n 2))
(defn fast-mult [a b]
  (cond (= b 0) 0
        (even? b) (double (fast-mult a (halve b)))
        :else (+ a (fast-mult a (- b 1)))))

;; Exercise 1.18
(defn mult-iter [a b acc]
  (cond (= b 0) acc
        (even? b) (recur (double a)
                         (halve b)
                         acc)
        :else (recur a
                     (- b 1)
                     (+ a acc))))
(defn mult [a b]
  (mult-iter a b 0))

;; Exercise 1.19
;; p' = p^2 + q^2
;; q' = 2pq + q^2
(defn fib-iter [a b p q count]
  (cond (= count 0) b
        (even? count) (recur a
                             b
                             (+ (square p) (square q))
                             (+ (* 2 p q) (square q))
                             (/ count 2))
        :else (recur (+ (* b q) (* a q) (* a p))
                     (+ (* b p) (* a q))
                     p
                     q
                     (- count 1))))
(defn fib [n]
  (fib-iter 1 0 0 1 n))
(defn gcd [a b]
  (if (= b 0)
    a
    (recur b (rem a b))))

;; Exercise 1.20
;; normal-order: ???
;; ???
;; applicative-order: 4 operations
;; (gcd 206 40)
;; (gcd 40 6)
;; (gcd 6 4)
;; (gcd 4 2)
;; (gcd 2 0)
;; 2
