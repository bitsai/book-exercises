(def cur-track (ref "Mars, Bringer of War"))
(deref cur-track)
@cur-track
(ref-set cur-track "Venus, Bringer of Peace")
(dosync (ref-set cur-track "Venus, Bringer of Peace"))
(def cur-track (ref "Venus, Bringer of Peace"))
(def cur-composer (ref "Holst"))
(dosync
 (ref-set cur-track "Credo")
 (ref-set cur-composer "Byrd"))
(defstruct msg :sender :text)
(struct msg "stu" "test")
(def msgs (ref ()))
(defn naive-add-msg [msg]
  (dosync (ref-set msgs (cons msg @msgs))))
(defn add-msg [msg]
  (dosync (alter msgs conj msg)))
(add-msg (struct msg "user 1" "hello"))
(add-msg (struct msg "user 2" "howdy"))
(defn add-msg-commute [msg]
  (dosync (commute msgs conj msg)))
(def counter (ref 0))
(defn next-counter [] (dosync (alter counter inc)))
(next-counter)
(next-counter)
(def validate-msg-list
  (partial every? #(and (:sender %) (:text %))))
(def msgs (ref () :validator validate-msg-list))
(add-msg "not a valid msg")
@msgs
(add-msg (struct msg "stu" "legit msg"))

(def cur-track (atom "Venus, Bringer of Peace"))
(deref cur-track)
@cur-track
(reset! cur-track "Credo")
(def cur-track (atom {:title "Credo", :composer "Byrd"}))
(reset! cur-track {:title "Spem in Alium", :composer "Tallis"})
(swap! cur-track assoc :title "Sancte Deus")

(def counter (agent 0))
(send counter inc)
@counter
(def counter (agent 0 :validator number?))
(send counter (fn [_] "boo"))
@counter
(clear-agent-errors counter)
@counter
(def backup-agent (agent "output/msgs-backup.clj"))
(defn add-msg-with-backup [msg]
  (dosync
   (let [snapshot (commute msgs conj msg)]
     (send-off backup-agent (fn [filename]
			      (spit filename snapshot)
			      filename))
     snapshot)))
(add-msg-with-backup (struct msg "john" "msg 1"))
(add-msg-with-backup (struct msg "jane" "msg 2"))

(def foo 10)
foo
(.start (Thread. (fn [] (println foo))))
(binding [foo 42] foo)
(defn print-foo [] (println foo))
(let [foo "let foo"] (print-foo))
(binding [foo "bound foo"] (print-foo))
(defn slow-double [n]
  (Thread/sleep 100)
  (* n 2))
(defn calls-slow-double []
  (map slow-double [1 2 1 2 1 2]))
(time (dorun (calls-slow-double)))
(defn demo-memoize []
  (time
   (dorun
    (binding [slow-double (memoize slow-double)]
      (calls-slow-double)))))
(demo-memoize)

;; I took the example code for Snake and turned it into the Light Cycle project