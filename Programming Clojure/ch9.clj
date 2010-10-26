(use '[clojure.contrib.seq :only (indexed)])
(defn index-filter [pred coll]
  (when pred (for [[idx elt] (indexed coll) :when (pred elt)] idx)))
(defn #^{:test (fn []
		 (assert (nil? (index-of-any #{\a} nil)))
		 (assert (nil? (index-of-any #{\a} "")))
		 (assert (nil? (index-of-any nil "foo")))
		 (assert (nil? (index-of-any #{} "foo")))
		 (assert (zero? (index-of-any #{\z \a} "zzabyycdxx")))
		 (assert (= 3 (index-of-any #{\b \y} "zzabyycdxx")))
		 (assert (nil? (index-of-any #{\z} "aba"))))}
  index-of-any
  [pred coll]
  (first (index-filter pred coll)))
(test #'index-of-any)
(defn
  #^{:test (fn []
	     (assert (nil? (busted))))}
  busted [] "busted")
(test #'busted)
(use '[clojure.test])
(deftest test-index-of-any-with-nil-args
  (is (nil? (index-of-any #{\a} nil)))
  (is (nil? (index-of-any nil "foo"))))
(deftest test-index-of-any-with-empty-args
  (is (nil? (index-of-any #{\a} "")))
  (is (nil? (index-of-any #{} "foo"))))
(deftest test-index-of-any-with-match
  (is (zero? (index-of-any #{\z \a} "zzabyycdxx")))
  (is (= 3 (index-of-any #{\b \y} "zzabyycdxx"))))
(deftest test-index-of-any-without-match
  (is (nil? (index-of-any #{\z} "aba"))))
(run-tests)
(deftest test-that-demonstrates-failure
  (is (= 5 (+ 2 2))))
(run-tests)
(deftest test-that-demonstrates-eror-message
  (is (= 3 Math/PI) "PI is an integer!"))
(run-tests)
(deftest test-divide-by-zero
  (is (thrown? ArithmeticException (/ 5 0))))
(run-tests)

(def db {:classname "org.hsqldb.jdbcDriver"
	 :subprotocol "hsqldb"
	 :subname "file:snippet-db"})
(use 'clojure.contrib.sql)
(defn create-snippets []
  (create-table
   :snippets
   [:id :int "IDENTITY" "PRIMARY KEY"]
   [:body :varchar "NOT NULL"]
   [:created_at :datetime]))
(create-snippets)
(with-connection db (create-snippets))
(with-connection db (create-snippets))
(defn now [] (java.sql.Timestamp. (.getTime (java.util.Date.))))
(defn insert-snippets []
  (let [timestamp (now)]
    (seq
     (insert-values
      :snippets
      [:body :created_at]
      ["(println :boo)" timestamp]
      ["(defn foo [] 1)" timestamp]))))
(with-connection db (insert-snippets))
(defn print-snippets []
  (with-query-results res ["select * from snippets"]
    (println res)))
(with-connection db (print-snippets))
(defn select-snippets []
  (with-query-results res ["select * from snippets"] res))
(with-connection db (select-snippets))
(defn select-snippets []
  (with-connection db
    (with-query-results res ["select * from snippets"] (doall res))))
(select-snippets)
(defn sql-query [q]
  (with-query-results res q (doall res)))
(with-connection db (sql-query ["select body from snippets"]))
(defn last-created-id []
  (first (vals (first (sql-query ["CALL IDENTITY()"])))))
(defn insert-snippet [body]
  (with-connection db
    (transaction
     (insert-values
      :snippets
      [:body :created_at]
      [body (now)])
     (last-created-id))))
(insert-snippet "(+ 1 1)")
(insert-snippet "(ref true)")
