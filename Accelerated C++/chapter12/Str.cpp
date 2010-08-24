#include <algorithm>
#include <cctype>
#include <stdexcept>
#include <vector>

#include "Str.hpp"

#include <iostream>

using namespace std;

// Public functions
Str& Str::operator =(const Str& rhs) {
  if (&rhs != this) {
    uncreate();
    create(rhs.begin(), rhs.end());
  }

  return *this;
}

Str::size_type Str::copy(iterator s, size_type n, size_type pos) const {
  if (pos > size()) throw out_of_range("pos > size()");
  size_t copy_length = min(n, size() - pos);
  ::copy(chars + pos, chars + copy_length, s);
  return copy_length;
}

Str& Str::operator +=(const Str& s) {
  size_type new_length = length + s.size();
  iterator new_chars = alloc.allocate(new_length);
  uninitialized_copy(chars, chars + length - 1, new_chars);
  uninitialized_copy(s.begin(), s.end(), new_chars + length - 1);
  alloc.construct(new_chars + new_length - 1, '\0');

  uncreate();
  
  length = new_length;
  chars = new_chars;
}

Str Str::substr(size_type pos, size_type n) const {
  return Str(chars + pos, chars + pos + n);
}

// Private functions
void Str::create(size_type n, char val) {
  length = n + 1;
  chars = alloc.allocate(length);
  uninitialized_fill(chars, chars + length - 1, val);
  alloc.construct(chars + length - 1, '\0');
}

void Str::uncreate() {
  if (chars) {
    iterator it = chars + length;
    while (it != chars) alloc.destroy(--it);
    alloc.deallocate(chars, length);
  }

  chars = 0;
  length = 0;
}

// Other functions
ostream& operator <<(ostream& os, const Str& s) {
  for (Str::size_type i = 0; i != s.size(); ++i) os << s[i];
  return os;
}

ostream_iterator<char>& operator <<(ostream_iterator<char>& osi, const Str& s) {
  copy(s.begin(), s.end(), osi);
  return osi;
}

static int is_newline(int c) {
  return (c == '\n');
}

static istream& read_until(istream& is, Str& s, int is_stop_char(int)) {
  vector<char> data;

  char c;
  while (is.get(c) && isspace(c)); 

  if (is) {
    do data.push_back(c);
    while (is.get(c) && !is_stop_char(c));
    if (is) is.unget();
  }

  s = Str(data.begin(), data.end());

  return is;
}

istream& operator >>(istream& is, Str& s) {
  return read_until(is, s, isspace);
}

istream& getline(istream&is, Str& s) {
  return read_until(is, s, is_newline);
}

Str operator +(const Str& s, const Str& t) {
  Str r = s;
  r += t;
  return r;
}

Str operator +(const char* s, const Str& t) {
  Str r(s, s + strlen(s));
  r += t;
  return r;
}

Str operator +(const Str& s, const char* t) {
  Str r = s;
  r += Str(t, t + strlen(t));
  return r;
}
