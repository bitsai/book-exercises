#include <algorithm>
#include <cctype>
#include <iostream>

#include "Str.h"
#include "urls.h"
#include "Vec.h"

using namespace std;

int main() {
  Str s;
  while (getline(cin, s)) {
    Vec<Str> v = find_urls(s);

    for (Vec<Str>::const_iterator i = v.begin(); i != v.end(); ++i)
      cout << *i << endl;
  }

  return 0;
}
