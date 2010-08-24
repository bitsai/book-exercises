#include <algorithm>
#include <iostream>
#include <iterator>
#include <string>
#include <vector>

using namespace std;

bool is_palindrome(const string& word) {
  string reversed;
  reverse_copy(word.begin(), word.end(), back_inserter(reversed));
  return (word == reversed);
}

int main() {
  vector<string> palindromes;
  string longest_palindrome;
  string s;
  
  while (cin >> s)
    if (is_palindrome(s)) {
      palindromes.push_back(s);

      if (s.length() > longest_palindrome.length())
	longest_palindrome = s;
    }
  

  cout << "\nPalindromes:" << endl;

  for (vector<string>::const_iterator it = palindromes.begin(); 
       it != palindromes.end(); ++it)
    cout << *it << endl;

  cout << "Longest: " << longest_palindrome << endl;
  
  return 0;
}
