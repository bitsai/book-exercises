#include <cctype>
#include <iostream>
#include <vector>

#include "split.h"
#include "Str.hpp"

using namespace std;

int main() {
  Str s;

  while (getline(cin, s)) {
    vector<Str> v = split(s);

    for (vector<Str>::size_type i = 0; i != v.size(); ++i)
      cout << v[i] << endl;
  }

  return 0;
}
