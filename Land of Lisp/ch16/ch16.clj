(defn add [a b]
  (let [x (+ a b)]
    (println "The sum is" x)
    x))
(add 2 3)
(defmacro let1 [var val & body]
  `(let [~var ~val]
     ~@body))
(let [foo (+ 2 3)]
  (* foo foo))
(let1 foo (+ 2 3)
      (* foo foo))

(let1 foo (+ 2 3)
      (println "Lisp is awesome!")
      (* foo foo))

(defn add [a b]
  (let1 x (+ a b)
        (println "The sum is" x)
        x))
(macroexpand '(let1 foo (+ 2 3) (* foo foo)))

(defn my-length [lst]
  (letfn [(f [[x & xs] acc]
            (if x
              (recur xs (inc acc))
              acc))]
    (f lst 0)))

(defmacro split [val yes no]
  `(if (seq ~val)
     (let [~'head (first ~val)
           ~'tail (rest ~val)]
       ~yes)
     ~no))
(split [2 3]
       (println "This can be split into" head "and" tail)
       (println "This cannot be split"))
(split []
       (println "This can be split into" head "and" tail)
       (println "This cannot be split"))
(defn my-length [lst]
  (letfn [(f [lst acc]
            (split lst
                   (recur tail (inc acc))
                   acc))]
    (f lst 0)))

(split (do (println "Lisp rocks!")
           [2 3])
       (println "This can be split into" head "and" tail)
       (println "This cannot be split"))
(macroexpand '(split (do (println "Lisp rocks!")
                         [2 3])
                     (println "This can be split into" head "and" tail)
                     (println "This cannot be split")))
(defmacro split [val yes no]
  `(let1 ~'x ~val
         (if (seq ~'x)
           (let [~'head (first ~'x)
                 ~'tail (rest ~'x)]
             ~yes)
           ~no)))
(split (do (println "Lisp rocks!")
           [2 3])
       (println "This can be split into" head "and" tail)
       (println "This cannot be split"))

(let1 x 100
      (split [2 3]
             (+ x head)
             nil))
(macroexpand '(split [2 3]
                     (+ x head)
                     nil))
(gensym)
(defmacro split [val yes no]
  (let1 g (gensym)
        `(let1 ~g ~val
               (if (seq ~g)
                 (let [~'head (first ~g)
                       ~'tail (rest ~g)]
                   ~yes)
                 ~no))))
(macroexpand '(split [2 3]
                     (+ x head)
                     nil))
(macroexpand '(split [2 3]
                     (+ x head)
                     nil))

(defn pairs [lst]
  (partition 2 lst))
(pairs '[a b c d e f])
(defmacro recurse [vars & body]
  (let1 p (pairs vars)
        `(letfn [(~'self ~(vec (map first p))
                   ~@body)]
           (~'self ~@(map second p)))))
(recurse [n 9]
         (if (zero? n)
           (println "lift-off!")
           (do (println n)
               (recur (dec n)))))
(defn my-length [lst]
  (recurse [lst lst
            acc 0]
           (split lst
                  (recur tail (inc acc))
                  acc)))

(defn my-length [lst]
  (reduce (fn [acc _] (inc acc))
          0
          lst))
