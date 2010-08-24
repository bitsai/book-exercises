#include <cctype>
#include <iostream>
#include <iterator>

using std::isspace;

#include "Str.h"

using std::istream;
using std::istream_iterator;
using std::ostream;

ostream& operator<<(ostream& os, const Str& s) {
  for (Str::size_type i = 0; i != s.size(); ++i) os << s[i];
  return os;
}

// this code won't compile quite yet
istream& operator>>(istream& is, Str& s) {
  // obliterate existing value(s)
  s.data.clear();

  // read and discard leading whitespace
  char c;
  while (is.get(c) && isspace(c)); // nothing to do, except testing the condition

  // if still something to read, do so until next whitespace character
  if (is) {
    do s.data.push_back(c);      // compile error! data is private
    while (is.get(c) && !isspace(c));

    // if we read whitespace, then put it back on the stream
    if (is) is.unget();
  }

  return is;
}

Str operator+(const Str& s, const Str& t) {
  Str r = s;
  r += t;
  return r;
}

istream& getline(istream&is, Str& s) {
  s.data.clear();

  char c;
  while (is.get(c) && isspace(c)); // nothing to do, except testing the condition

  // if still something to read, do so until next whitespace character
  if (is) {
    do s.data.push_back(c);
    while (is.get(c) && c != '\n');

    // if we read whitespace, then put it back on the stream
    if (is) is.unget();
  }

  return is;
}
