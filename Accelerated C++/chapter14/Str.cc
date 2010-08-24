#include <cctype>
#include <iostream>
#include <iterator>

#include "Str.h"

using namespace std;

ostream& operator<<(ostream& os, const Str& s) {
  for (Str::size_type i = 0; i != s.size(); ++i) os << s[i];
  return os;
}

istream& operator>>(istream& is, Str& s) {
  s.data->clear();

  char c;
  while (is.get(c) && isspace(c));

  if (is) {
    do	s.data->push_back(c);
    while (is.get(c) && !isspace(c));
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
  s.data->clear();

  char c;
  while (is.get(c) && isspace(c));

  if (is) {
    do s.data->push_back(c);
    while (is.get(c) && c != '\n');
    if (is) is.unget();
  }

  return is;
}
