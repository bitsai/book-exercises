#include <algorithm>
#include <iostream>
#include <iterator>
#include <string>
#include <vector>

#include "pics.h"

using std::cout;
using std::copy;
using std::endl;
using std::ostream_iterator;
using std::string;
using std::vector;

int main() {
  vector<string> v;
  vector<string> f = frame(v);

  for (vector<string>::const_iterator i = f.begin();
       i != f.end(); ++i)
    cout << *i << endl;

  return 0;
}
