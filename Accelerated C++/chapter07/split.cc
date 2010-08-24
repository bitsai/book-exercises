#include <cctype>
#include <string>
#include <vector>

#include "split.h"

using std::vector;
using std::string;

#ifndef _MSC_VER
using std::isspace;
#endif

vector<string> split(const string& s) {
  vector<string> ret;
  typedef string::size_type string_size;
  string_size i = 0;

  // invariant: we have processed characters `['original value of `i', `i)'
  while (i != s.size()) {
    // ignore leading blanks
    // invariant: characters in range `['original `i', current `i)' are all spaces
    while (i != s.size() && isspace(s[i]))
      ++i;

    // find end of next word
    string_size j = i;
    // invariant: none of the characters in range `['original `j', current `j)' is a space
    while (j != s.size() && !isspace(s[j]))
      ++j;

    // if we found some nonwhitespace characters
    if (i != j) {
      // copy from `s' starting at `i' and taking `j' `\-' `i' chars
      ret.push_back(s.substr(i, j - i));
      i = j;
    }
  }

  return ret;
}
