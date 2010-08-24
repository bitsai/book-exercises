#include <iostream>
#include <map>
#include <string>

using std::cin;
using std::cout;
using std::endl;
using std::map;
using std::string;

int main() {
  string s;
  map<string, int> counters; // store each word and an associated counter

  // read the input, keeping track of each word and how often we see it
  while (cin >> s)
    ++counters[s];

  // write the words and associated counts
#ifdef _MSC_VER
  for (std::map<string, int>::const_iterator it = counters.begin();
#else
       for (map<string, int>::const_iterator it = counters.begin();
#endif
	    it != counters.end(); ++it) {
	 cout << it->first << "\t" << it->second << endl;
       }

	 return 0;
       }
