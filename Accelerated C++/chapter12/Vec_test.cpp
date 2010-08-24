#include <iostream>
#include <string>

#include "Vec.h"

using namespace std;

void print_vec(const Vec<char>& v) {
  for (size_t i = 0; i != v.size(); ++i) cout << v[i];
  cout << endl;
}

int main() {
  // iterators constructor
  char a0[] = "123";
  Vec<char> v0(a0, a0 + 3);
  print_vec(v0);

  // insert()
  char a1[] = "1256";
  char a2[] = "34";
  Vec<char> v1(a1, a1 + 4);
  Vec<char> v2(a2, a2 + 2);
  v1.insert(v1.begin() + 2, v2.begin(), v2.end());
  print_vec(v1);

  // assign()
  char a3[] = "123";
  char a4[] = "456";
  Vec<char> v3(a3, a3 + 3);
  Vec<char> v4(a4, a4 + 3);
  v3.assign(v4.begin(), v4.end());
  print_vec(v3);

  // initialize from string
  string s = "123456";
  Vec<char> v5(s.begin(), s.end());
  print_vec(v5);

  return 0;
}
