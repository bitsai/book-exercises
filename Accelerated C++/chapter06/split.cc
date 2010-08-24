#include <algorithm>
#include <cctype>
#include <string>
#include <vector>

#include "split.h"

using std::find_if;
using std::string;
using std::vector;

#ifndef _MSC_VER
using std::isspace;
#endif

// `true' if the argument is whitespace, `false' otherwise
bool space(char c) {
  return isspace(c);
}

// `false' if the argument is whitespace, `true' otherwise
bool not_space(char c) {
  return !isspace(c);
}

vector<string> split(const string& str) {
  typedef string::const_iterator iter;
  vector<string> ret;

  iter i = str.begin();
  while (i != str.end()) {

    // ignore leading blanks
    i = find_if(i, str.end(), not_space);

    // find end of next word
    iter j = find_if(i, str.end(), space);

    // copy the characters in `[i,' `j)'
    if (i != str.end())
      ret.push_back(string(i, j));

    i = j;
  }

  return ret;
}

