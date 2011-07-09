(ns lazy)

(defmacro lazy-cons [a d]
  `(delay (list ~a ~d)))

(defn lazy-car [x]
  (first (force x)))

(defn lazy-cdr [x]
  (second (force x)))

(defn lazy-nil []
  (delay nil))

(defn lazy-empty? [x]
  (empty? (force x)))

(defn make-lazy [lst]
  (delay (when lst
           (list (first lst) (make-lazy (next lst))))))

(defn lazy-take [n lst]
  (when-not (or (zero? n) (lazy-empty? lst))
    (cons (lazy-car lst) (lazy-take (dec n) (lazy-cdr lst)))))

(defn lazy-take-all [lst]
  (when-not (lazy-empty? lst)
    (cons (lazy-car lst) (lazy-take-all (lazy-cdr lst)))))

(defn lazy-mapcar [fun lst]
  (delay (when-not (lazy-empty? lst)
           (list (fun (lazy-car lst))
                 (lazy-mapcar fun (lazy-cdr lst))))))

(defn lazy-mapcan [fun lst]
  (let [f (fn f [lst-cur]
            (if (lazy-empty? lst-cur)
              (force (lazy-mapcan fun (lazy-cdr lst)))
              (list (lazy-car lst-cur) (delay (f (lazy-cdr lst-cur))))))]
    (delay (when-not (lazy-empty? lst)
             (f (fun (lazy-car lst)))))))

(defn lazy-find-if [fun lst]
  (when-not (lazy-empty? lst)
    (let [x (lazy-car lst)]
      (if (fun x)
        x
        (recur fun (lazy-cdr lst))))))

(defn lazy-nth [n lst]
  (if (zero? n)
    (lazy-car lst)
    (recur (dec n) (lazy-cdr lst))))
