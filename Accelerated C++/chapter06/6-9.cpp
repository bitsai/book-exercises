#include <algorithm>
#include <iostream>
#include <string>
#include <vector>

using namespace std;

string target = "";

void cat(string s) {
  target += s;
}

int main() {
  vector<string> words = vector<string>(3, "bitch");
  for_each(words.begin(), words.end(), cat);
  cout << target << endl;
  
  return 0;
}
