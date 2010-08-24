#include <iostream>
#include <map>
#include <string>
#include <vector>

using namespace std;

int main() {
  string s;
  map<string, int> counters; // store each word and an associated counter
  map<int, vector<string> > words_by_freq;

  // read the input, keeping track of each word and how often we see it
  while (cin >> s)
    ++counters[s];

  for (map<string, int>::const_iterator it = counters.begin();
       it != counters.end(); ++it)
    words_by_freq[it->second].push_back(it->first);

  for (map<int, vector<string> >::const_iterator i = words_by_freq.begin();
       i != words_by_freq.end(); ++i) {
    cout << "\nFrequency: " << i->first << endl;

    for (vector<string>::const_iterator j = i->second.begin();
	 j != i->second.end(); ++j)
      cout << *j << endl;
  }
  
  return 0;
}
