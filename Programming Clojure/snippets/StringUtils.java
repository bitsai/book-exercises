/***
 * Excerpted from "Programming Clojure",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/shcloj for more book information.
***/
// From Apache Commons Lang, http://commons.apache.org/lang/
public static int indexOfAny(String str, char[] searchChars) {
    if (isEmpty(str) || ArrayUtils.isEmpty(searchChars)) {
	return -1;
    }
    for (int i = 0; i < str.length(); i++) {
	char ch = str.charAt(i);
	for (int j = 0; j < searchChars.length; j++) {
	    if (searchChars[j] == ch) {
		return i;
	    }
	}
    }
    return -1;
}
