#pragma once

#include <algorithm>
#include <cstring>
#include <cctype>
#include <iostream>

using std::isspace;

#include "Vec.h"

class Str {
  // input operator implemented in 12.3.2/216
  friend std::istream& operator>>(std::istream&, Str&);
  friend std::istream& getline(std::istream&, Str&);

 public:
  Str& operator+=(const Str& s) {
    std::copy(s.data.begin(), s.data.end(), std::back_inserter(data));
    return *this;
  }

  // as before
  typedef Vec<char>::size_type size_type;

  Str() { }

 Str(size_type n, char c): data(n, c) { }
  
  Str(const char* cp) { std::copy(cp, cp + std::strlen(cp), std::back_inserter(data)); }

  template <class In> Str(In i, In j) { std::copy(i, j, std::back_inserter(data)); }

  char& operator[](size_type i) { return data[i]; }
  const char& operator[](size_type i) const { return data[i]; }
  size_type size() const { return data.size(); }

  typedef char* iterator;
  typedef const char* const_iterator;

  iterator begin() { return data.begin(); }
  const_iterator begin() const { return data.begin(); }

  iterator end() { return data.end(); }
  const_iterator end() const { return data.end(); }

 private:
  Vec<char> data;
};

// output operator implemented in 12.3.2/216
std::ostream& operator<<(std::ostream&, const Str&);

Str operator+(const Str&, const Str&);

inline bool operator<(const Str& lhs, const Str& rhs) {
  return std::lexicographical_compare(lhs.begin(), lhs.end(), rhs.begin(), rhs.end());
}

inline bool operator>(const Str& lhs, const Str& rhs) {
  return std::lexicographical_compare(rhs.begin(), rhs.end(), lhs.begin(), lhs.end());
}

inline bool operator<=(const Str& lhs, const Str& rhs) {
  return !std::lexicographical_compare(rhs.begin(), rhs.end(), lhs.begin(), lhs.end());
}

inline bool operator>=(const Str& lhs, const Str& rhs) {
  return !std::lexicographical_compare(lhs.begin(), lhs.end(), rhs.begin(), rhs.end());
}

inline bool operator==(const Str& lhs, const Str& rhs) {
  return lhs.size() == rhs.size() && 
  std::equal(lhs.begin(), lhs.end(), rhs.begin());
}

inline bool operator!=(const Str& lhs, const Str& rhs) {
  return !(lhs == rhs);
}
