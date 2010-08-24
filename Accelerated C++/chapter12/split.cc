#include <cctype>
#include <vector>

#include "split.h"
#include "Str.hpp"

using namespace std;

vector<Str> split(const Str& s) {
  vector<Str> ret;
  typedef Str::size_type string_size;
  string_size i = 0;

  while (i != s.size()) {
    while (i != s.size() && isspace(s[i])) ++i;

    string_size j = i;

    while (j != s.size() && !isspace(s[j])) ++j;

    if (i != j) {
      ret.push_back(s.substr(i, j - i));
      i = j;
    }
  }

  return ret;
}
