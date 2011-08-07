(if [] ;; true in Clojure
  'i-am-true
  'i-am-false)
(if [1]
  'i-am-true
  'i-am-false)
(defn my-length [lst]
  (if (seq lst)
    (inc (my-length (rest lst)))
    0))
(my-length '[list with four symbols])

(= [] nil) ;; false in Clojure
(= [] ())
(= [] 'nil) ;; false in Clojure

(if (= (+ 1 2) 3)
  'yup
  'nope)
(if (= (+ 1 2) 4)
  'yup
  'nope)
(if [1]
  'the-list-has-stuff-in-it
  'the-list-is-empty)
(if [] ;; true in Clojure
  'the-list-has-stuff-in-it
  'the-list-is-empty)
(if (odd? 5)
  'odd-number
  'even-number)
(if (odd? 5)
  'odd-number
  (/ 1 0))
(def *number-was-odd* (atom false))
(if (odd? 5)
  (do (reset! *number-was-odd* true)
      'odd-number)
  'even-number)
@*number-was-odd*

(def *number-is-odd* (atom false))
(when (odd? 5)
  (reset! *number-is-odd* true)
  'odd-number)
@*number-is-odd*
(when-not (odd? 4)
  (reset! *number-is-odd* false)
  'even-number)
@*number-is-odd*

(def *arch-enemy* (atom nil))
(defn pudding-eater [p]
  (cond (= p 'henry) (do (reset! *arch-enemy* 'stupid-lisp-alien)
                         '[curse you lisp alien - you ate my pudding])
        (= p 'johnny) (do (reset! *arch-enemy* 'useless-old-johnny)
                          '[i hope you choked on my pudding johnny])
        :else '[why you eat my pudding stranger ?]))
(pudding-eater 'johnny)
@*arch-enemy*
(pudding-eater 'george-clooney)

(defn pudding-eater [p]
  (case p
    'henry (do (reset! *arch-enemy* 'stupid-lisp-alien)
               '[curse you lisp alien - you ate my pudding])
    'johnny (do (reset! *arch-enemy* 'useless-old-johnny)
                '[i hope you choked on my pudding johnny])
    '[why you eat my pudding stranger ?]))

(and (odd? 5) (odd? 7) (odd? 9))
(or (odd? 4) (odd? 7) (odd? 8))
(def *is-it-even* (atom false))
(or (odd? 4) (reset! *is-it-even* true))
@*is-it-even*
(def *is-it-even* (atom false))
(or (odd? 5) (reset! *is-it-even* true))
@*is-it-even*

(if (some #{1} [3 4 1 5])
  'one-is-in-the-list
  'one-is-not-in-the-list)
(some #{1} [3 4 1 5]) ;; returns 1 in Clojure
(if (some #{nil} [3 4 nil 5]) ;; returns nil (false) in Clojure
  'nil-is-in-the-list
  'nil-is-not-in-the-list)
(defn find-if [pred coll]
  (first (filter pred coll)))
(find-if odd? [2 4 5 6])
(if (find-if odd? [2 4 5 6])
  'there-is-an-odd-number
  'there-is-no-odd-number)
(find-if nil? [2 4 nil 6])

(def fruit 'apple)
(cond (= fruit 'apple) 'its-an-apple
      (= fruit 'orange) 'its-an-orange)
(= 'apple 'apple)
(= [1 2 3] [1 2 3])
(= [1 2 3] (cons 1 (cons 2 (cons 3 []))))
(= 5 5)
(= 2.5 2.5)
(= "foo" "foo")
(= \a \a)
(= 'foo 'foo)
(= 3.4 3.4)
(= \a \a)
(.equalsIgnoreCase "Bob Smith" "bob smith")
(= 0 0.0)
