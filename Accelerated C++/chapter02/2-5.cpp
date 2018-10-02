#include <iostream>

using std::cin;
using std::endl;
using std::cout;

int main() {
  cout << "how to rows? ";

  int len;
  cin >> len;

  for (int i = 0; i < len; i += 1) {
    for (int j = 0; j < len; j += 1) {
      cout << "*";
    }
    cout << endl;
  }
  
  cout << endl;

  const int mid = len / 2;

  for (int i = 0; i < mid; i += 1) {
    for (int j = 0; j < len; j += 1) {
      if (j >= mid - i & j <= mid + i) {
        cout << "*";
      } else {
        cout << " ";
      }
    }
    cout << endl;
  }
  return 0;
}
