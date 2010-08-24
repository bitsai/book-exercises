#include <iostream>

#include "List.hpp"

using namespace std;

int main() {
  string arr1[] = {"Hello", "to", "the", "world!"};
  string arr2[] = {"a", "b", "c"};
  string arr3[] = {"x", "y", "z"};

  cout << list<string>() << endl;
  cout << list<string>(NULL, NULL) << endl;
  cout << list<string>(arr1, arr1 + 4) << endl;
  cout << reverse(list<string>(arr1, arr1 + 4)) << endl;
  cout << append(list<string>(arr2, arr2 + 3), list<string>(arr3, arr3 + 3)) 
       << endl;

  return 0;
}
