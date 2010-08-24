/***
 * Excerpted from "Programming Clojure",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/shcloj for more book information.
***/
public class StringUtils { 
  public static boolean isBlank(String str) { 
    int strLen; 
    if (str == null || (strLen = str.length()) == 0) { 
      return true; 
    }  
    for (int i = 0; i < strLen; i++) { 
	    if ((Character.isWhitespace(str.charAt(i)) == false)) { 
	      return false; 
	    }
    }
    return true;
  }
}