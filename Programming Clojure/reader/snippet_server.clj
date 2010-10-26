(ns reader.snippet-server
  (:use compojure examples.snippet))

(defn layout [title & body]
  (html
   [:head
    [:title title]
    (include-js "/public/javascripts/code-highlighter.js"
		"/public/javascripts/clojure.js")
    (include-css "/public/stylesheets/code-highlighter.css")]
   [:body
    [:h2 title]
    body]))

(defn new-snippet []
  (layout "Create a Snippet"
   (form-to [:post "/"]
	    (text-area {:rows 20 :cols 73} "body")
	    [:br]
	    (submit-button "Save"))))

(defn show-snippet [id]
  (layout (str "Snippet " id)
	  (let [snippet (select-snippet id)]
	    (html
	     [:div [:pre [:code.clojure (:body snippet)]]]
	     [:div (:created_at snippet)]))))

(defn create-snippet [body]
  (if-let [id (insert-snippet body)]
    (redirect-to (str "/" id))
    (redirect-to "/")))

(defroutes snippet-app
  "Create and view snippets."
  (GET "/ping"
       "pong")
  (GET "/"
       (new-snippet))
  (GET "/:id"
       (show-snippet (params :id)))
  (POST "/"
	(create-snippet (:body params)))
  (GET "/public/*"
       (or (serve-file (params :*)) :next))
  (ANY "*"
       (page-not-found)))

(run-server {:port 8080}
	    "/*" (servlet snippet-app))
