#!/bin/sh
#---
# Excerpted from "Programming Clojure",
# published by The Pragmatic Bookshelf.
# Copyrights apply to this code. It may not be used to create training material, 
# courses, books, articles, and the like. Contact us if you are in doubt.
# We make no guarantees that this code is fit for any purpose. 
# Visit http://www.pragmaticprogrammer.com/titles/shcloj for more book information.
#---
java -cp .:lib/commons-io-1.4.jar:lib/commons-fileupload-1.2.1.jar:lib/commons-codec-1.3.jar:lib/jline-0.9.94.jar:lib/clojure.jar:lib/clojure-contrib.jar:lib/compojure.jar:lib/hsqldb.jar:lib/jetty-6.1.14.jar:lib/jetty-util-6.1.14.jar:lib/servlet-api-2.5-6.1.14.jar jline.ConsoleRunner clojure.lang.Repl reader/snippet_server.clj