#include <iostream>

#include "8-5-xref-utils.hpp"
#include "split.h"

using namespace std;

int main() {
  // call `xref'
  my_ostream_iterator<pair<string, vector<int> > > out_it(cout, " ");
  xref(cin, split, out_it);
  return 0;
}
