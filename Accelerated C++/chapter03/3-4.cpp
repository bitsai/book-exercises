#include <iostream>
#include <string>

using namespace std;

int main() {
  typedef string::size_type str_sz;

  string longest;
  str_sz longest_length = 0;
  string shortest;
  str_sz shortest_length = 0;

  cout << "Words: ";
  string s;

  while (cin >> s) {
    if (longest_length == 0 || s.size() > longest_length) {
      longest = s;
      longest_length = s.size();
    } 

    if (shortest_length == 0 || s.size() < shortest_length) {
      shortest = s;
      shortest_length = s.size();
    }
  }

  cout << "Longest: " << longest << endl;
  cout << "Shortest: " << shortest << endl;

  return 0;
}		
