#include <map>
#include <iostream>
#include <string>
#include <vector>

#include "split.h"

using std::cin;            using std::cout;
using std::endl;           using std::getline;
using std::istream;        using std::string;
using std::vector;         using std::map;

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
#ifdef _MSC_VER
    for (std::vector<string>::const_iterator it = words.begin();
#else
	 for (vector<string>::const_iterator it = words.begin();
#endif
	      it != words.end(); ++it)
	   ret[*it].push_back(line_number);
	 }

    return ret;
  }

  int main() {
    // call `xref' using `split' by default
    map<string, vector<int> > ret = xref(cin);

    // write the results
#ifdef _MSC_VER
    for (std::map<string, vector<int> >::const_iterator it = ret.begin();
#else
	 for (map<string, vector<int> >::const_iterator it = ret.begin();
#endif
	      it != ret.end(); ++it) {
	   // write the word
	   cout << it->first << " occurs on line(s): ";

	   // followed by one or more line numbers
#ifdef _MSC_VER
	   std::vector<int>::const_iterator line_it = it->second.begin();
#else
	   vector<int>::const_iterator line_it = it->second.begin();
#endif
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
