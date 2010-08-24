#include <algorithm>
#include <iostream>
#include <iterator>
#include <vector>

#include "pics.h"
#include "Str.hpp"

using namespace std;

int main() {
  vector<Str> p;
  p.push_back("this is an");
  p.push_back("example");
  p.push_back("to");
  p.push_back("illustrate");
  p.push_back("framing");

  ostream_iterator<Str> ofile(cout, "\n");
  copy(p.begin(), p.end(), ofile);
  cout << endl;

  vector<Str> f = frame(p);
  copy(f.begin(), f.end(), ofile);
  cout << endl;

  vector<Str> h = hcat(frame(p), p);
  copy(h.begin(), h.end(), ofile);
  cout << endl;

  return 0;
}
