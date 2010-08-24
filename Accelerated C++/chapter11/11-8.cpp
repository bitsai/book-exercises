#include <iostream>

#include "List.hpp"

using namespace std;

int main() {
  list<int> l0 = list<int>();
  int a1[] = {1, 2};
  list<int> l1 = list<int>(a1, a1 + 2);
  int a2[] = {3, 4};
  list<int> l2 = list<int>(a2, a2 + 2);
  list<int> l3;
  l3 = append(l1, l2);
  
  cout << l0 << endl;
  cout << is_null(l0) << endl;
  cout << l1 << endl;
  cout << list_ref(l1, 0) << endl;
  cout << list_ref(l1, 1) << endl;
  cout << l3 << endl;
  cout << reverse(l3) << endl;
  cout << member<int>(2, l3) << endl;
  cout << member<int>(0, l3) << endl;
  
  cout << cons(l1, cons(l2, list< list<int> >())) << endl;

  return 0;
}
