#include <iostream>
#include <string>
#include <vector>

using namespace std;

istream& read_words(istream& in, vector<string>& words) {
  if (in) {
    words.clear();
    string word;
    
    while (in >> word)
      words.push_back(word);
    
    in.clear();
  }
  
  return in;
}

int main() {
  vector<string> words;

  read_words(cin, words);

  cout << "Num of words: " << words.size() << endl;

  sort(words.begin(), words.end());

  string prev_word = "";
  int count = 0;

  for (vector<string>::size_type i = 0; i < words.size(); ++i) {
    if (words[i] != prev_word) {
      if (prev_word != "")
	cout << prev_word << " appeared " << count << " times" << endl;

      prev_word = words[i];
      count = 1;
    }
    else
      ++count;
  }

  cout << prev_word << " appeared " << count << " times" << endl;

  return 0;
}
