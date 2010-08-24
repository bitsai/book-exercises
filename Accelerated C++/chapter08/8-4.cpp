#include <iostream>
#include <string>

#include "8-4.hpp"

using namespace std;

int main() {
  int x = 1;
  int y = 2;

  cout << x << endl;
  cout << y << endl;

  my_swap(x, y);

  cout << x << endl;
  cout << y << endl;
  
  return 0;
}
