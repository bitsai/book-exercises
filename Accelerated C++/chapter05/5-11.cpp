#include <iostream>
#include <string>
#include <vector>

using namespace std;

const string ascenders = "bdfhklt";
const string descenders = "gjpqy";

bool has_chars(const string& word, const string& chars) {
  for (string::size_type i = 0; i != word.length(); i++)
    for (string::size_type j = 0; j != chars.length(); j++)
      if (word[i] == chars[j])
	return true;

  return false;
}

int main() {
  vector<string> has_asc_or_dsc;
  string longest_word_without_asc_or_dsc;
  string s;

  while (cin >> s)
    if (has_chars(s, ascenders) || has_chars(s, descenders))
      has_asc_or_dsc.push_back(s);
    else if (s.length() > longest_word_without_asc_or_dsc.length())
      longest_word_without_asc_or_dsc = s;
  
  cout << "\nHas ascenders or descenders: " << endl;
  
  for (vector<string>::const_iterator it = has_asc_or_dsc.begin(); 
       it != has_asc_or_dsc.end(); ++it)
    cout << *it << endl;

  cout << "Longest word without ascenders or descenders: " 
       << longest_word_without_asc_or_dsc << endl;

  return 0;
}
