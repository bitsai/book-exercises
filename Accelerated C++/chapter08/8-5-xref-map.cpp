#include <iostream>

#include "8-5-xref-utils.hpp"
#include "split.h"

using namespace std;

int main() {
  // call `xref'
  map<string, vector<int> > ret;
  xref(cin, split, inserter(ret, ret.begin()));

  // write the results
  for (map<string, vector<int> >::const_iterator it = ret.begin();
       it != ret.end(); ++it) {
    // write the word
    cout << it->first << " occurs on line(s): ";

    // followed by one or more line numbers
    vector<int>::const_iterator line_it = it->second.begin();
    cout << *line_it;	// write the first line number

    ++line_it;

    // write the rest of the line numbers, if any
    while (line_it != it->second.end()) {
      cout << ", " << *line_it;
      ++line_it;
    }

    // write a new line to separate each word from the next
    cout << endl;
  }

  return 0;
}
