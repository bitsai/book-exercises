#include <cctype>
#include <iostream>
#include <string>
#include <vector>

#include "split.h"

using std::cin;
using std::cout;
using std::endl;
using std::getline;
using std::string;
using std::vector;

#ifndef _MSC_VER
using std::isspace;
#endif

int main() {
  string s;

  // read and split each line of input
  while (getline(cin, s)) {
    vector<string> v = split(s);

    // write each word in `v'
#ifdef _MSC_VER
    for (std::vector<string>::size_type i = 0; i != v.size(); ++i)
#else
      for (vector<string>::size_type i = 0; i != v.size(); ++i)
#endif
	cout << v[i] << endl;
  }

  return 0;
}
