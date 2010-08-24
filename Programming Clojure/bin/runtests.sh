#!/bin/sh
#---
# Excerpted from "Programming Clojure",
# published by The Pragmatic Bookshelf.
# Copyrights apply to this code. It may not be used to create training material, 
# courses, books, articles, and the like. Contact us if you are in doubt.
# We make no guarantees that this code is fit for any purpose. 
# Visit http://www.pragmaticprogrammer.com/titles/shcloj for more book information.
#---
java -Xmx1G -cp lib/clojure.jar:lib/clojure-contrib.jar:lib/hsqldb.jar:lib/ant.jar:lib/ant-launcher.jar:.:classes clojure.lang.Script examples/test.clj