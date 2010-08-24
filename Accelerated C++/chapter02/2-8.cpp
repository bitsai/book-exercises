#include <iostream>

using namespace std;

int main() {
  int product = 1;

  for (int i = 1; i < 10; ++i)
    product *= i;

  cout << "Product: " << product << endl;

  return 0;
}
