#pragma once

#include <algorithm>
#include <cctype>
#include <string>

using std::find_if;
using std::isspace;
using std::string;

inline bool space(char c) {
  return isspace(c);
}

inline bool not_space(char c) {
  return !isspace(c);
}

template <class Out>                             // changed
void split(const string& str, Out os) {          // changed
  typedef string::const_iterator iter;
  iter i = str.begin();
  
  while (i != str.end()) {
    // ignore leading blanks
    i = find_if(i, str.end(), not_space);

    // find end of next word
    iter j = find_if(i, str.end(), space);

    // copy the characters in `[i,' `j)'
    if (i != str.end())
      *os++ = string(i, j);   // changed

    i = j;
  }
}
