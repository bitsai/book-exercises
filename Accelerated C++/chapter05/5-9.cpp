#include <iostream>
#include <string>
#include <vector>

using namespace std;

bool has_uppercase(const string& word) {
  for (string::size_type i = 0; i != word.size(); ++i)
    if (isupper(word[i]))
      return true;

  return false;
}

void print_string_vector(const vector<string>& v) {
  for (vector<string>::const_iterator i = v.begin(); i != v.end(); ++i)
    cout << *i << endl;
}

int main() {
  string s;
  vector<string> no_uppercase;
  vector<string> uppercase;

  while (cin >> s)
    if (has_uppercase(s))
      uppercase.push_back(s);
    else
      no_uppercase.push_back(s);
  
  cout << "\nNo uppercase letters:" << endl;
  print_string_vector(no_uppercase);

  cout << "Has uppercase letters:" << endl;
  print_string_vector(uppercase);

  return 0;
}
