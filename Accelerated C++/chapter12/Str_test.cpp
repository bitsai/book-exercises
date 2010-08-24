#include <iostream>

#include "Str.hpp"

using namespace std;

int main() {
  // Default, num of char constructors
  cout << Str() << endl;
  cout << Str(3, 'A') << endl;

  // Copy constructor, assignment operator
  Str s1 = Str(3, 'X');
  Str s2 = Str(s1);
  cout << s2 << endl;
  Str s3;
  s3 = s2;
  cout << s3 << endl;

  // char*, iterators constructors
  Str s4 = "Hello";
  cout << s4 << endl;
  cout << Str(s4.begin(), s4.end()) << endl;

  // Indexed access, size()
  Str s5 = "world!";
  s5[0] = 'W';
  cout << s5 << endl;
  cout << s5.size() << endl;

  // c_str(), data(), copy()
  Str s6 = "Hello world!";
  char buf[80] = {0};
  const char* c_str = s6.c_str();
  const char* data = s6.data();
  s6[6] = 'W';
  cout << c_str << endl;
  cout << data << endl;
  cout << s6.copy(buf, 79) << endl;
  cout << buf << endl;

  // Relational operators
  Str s7 = "A";
  Str s8 = "Z";
  cout << (s7 < s8) << endl;
  cout << (s7 > s8) << endl;
  cout << (s7 <= s8) << endl;
  cout << (s7 >= s8) << endl;
  cout << (s7 == s8) << endl;
  cout << (s7 != s8) << endl;

  // Concatenation
  Str str9 = "Hello ";
  Str str10 = "World!";
  cout << (str9 + str10) << endl;
  cout << ("Hello " + str10) << endl;
  cout << (str9 + "World!") << endl;

  // Str as condition
  Str str11 = "";
  Str str12 = "Not empty";
  if (!str11) cout << "Yes" << endl;
  if (str12) cout << "Yes" << endl;

  // getline(), >>
  Str s13;
  cout << "Enter space-delimited text: ";
  getline(cin, s13);
  cout << s13 << endl;
  cout << "Enter space-delimited text: ";
  cin >> s13;
  cout << s13 << endl;

  // << via ostream_iterator
  Str s14 = "Hello World!";
  ostream_iterator<char> out_it(cout, "");
  out_it << s14 << "\n";

  // insert()
  Str s15 = "Hello !";
  Str s16 = "World";
  s15.insert(s15.begin() + 6, s16.begin(), s16.end());
  cout << s15 << endl;

  return 0;
}
