#include <algorithm>
#include <cctype>
#include <iostream>
#include <string>

using std::cin;             using std::cout;
using std::endl;            using std::equal;
using std::string;          using std::transform;

#ifndef _MSC_VER
using std::isspace;
#endif

bool is_palindrome(const string& s) {
  return equal(s.begin(), s.end(), s.rbegin());
}

int main() {
  string s;
  while (cin >> s) {
    if (is_palindrome(s))
      cout << s << " is a palindrome" << endl;
    else
      cout << s << " is not a palindrome" << endl;
  }

  return 0;
}
