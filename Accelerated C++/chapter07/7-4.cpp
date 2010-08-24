#include <map>
#include <iostream>
#include <string>
#include <sstream>
#include <vector>

#include "split.h"

using namespace std;

#define LINE_LENGTH 80

// find all the lines that refer to each word in the input
map<string, vector<int> > xref(istream& in,
			       vector<string> find_words(const string&) = split) {
  string line;
  int line_number = 0;
  map<string, vector<int> > ret;

  // read the next line
  while (getline(in, line)) {
    ++line_number;

    // break the input line into words
    vector<string> words = find_words(line);

    // remember that each word occurs on the current line
    for (vector<string>::const_iterator it = words.begin();
	 it != words.end(); ++it)
      ret[*it].push_back(line_number);
  }

  return ret;
}

int main() {
  // call `xref' using `split' by default
  map<string, vector<int> > ret = xref(cin);

  // write the results
  for (map<string, vector<int> >::const_iterator it = ret.begin();
       it != ret.end(); ++it) {
    stringstream ss;

    // write the word
    ss << it->first << " occurs on line(s): ";

    // followed by one or more line numbers
    vector<int>::const_iterator line_it = it->second.begin();

    ss << *line_it;	// write the first line number

    ++line_it;

    // write the rest of the line numbers, if any
    while (line_it != it->second.end()) {
      ss << ", " << *line_it;
      ++line_it;
    }

    string output = ss.str();

    for (string::size_type i = 0; i != output.size(); ++i) {
      cout << output[i];
      
      if ((i + 1) % LINE_LENGTH == 0)
	cout << endl;
    }

    // write a new line to separate each word from the next
    cout << endl;
  }

  return 0;
}
