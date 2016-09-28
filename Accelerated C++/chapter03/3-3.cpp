#include <iostream>
#include <string>
#include <vector>

using namespace std;

int main() {
  typedef vector<string>::size_type vec_sz;
  
  vector<string> words;
  vector<int> counts;

  cout << "Words: ";
  string s;

  while (cin >> s) {
    bool found = false;

    for (vec_sz i = 0; i < words.size(); ++i) {
      if (s == words[i]) {
	++counts[i];
	found = true;
      }
    }

    if (!found) {
      words.push_back(s);
      counts.push_back(1);
    }
  }

  for (vec_sz i = 0; i < words.size(); ++i)
    cout << words[i] << " appeared " << counts[i] << " times" << endl;

  return 0;
}
