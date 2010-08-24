#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

int main() {
  vector<int> integers;

  cout << "Integers: ";
  int x;
	
  while (cin >> x)
    integers.push_back(x);

  if (integers.size() == 0) {
    cout << endl << "No integers!" << endl;
    return 1;
  }
	
  sort(integers.rbegin(), integers.rend());
  typedef vector<int>::size_type vec_sz;

  cout << "1st quartile" << endl;
  for (vec_sz i = 0; i < integers.size() / 4; ++i)
    cout << integers[i] << endl;

  cout << "2nd quartile" << endl;
  for (vec_sz i = integers.size() / 4; i < integers.size() / 2; ++i)
    cout << integers[i] << endl;

  cout << "3rd quartile" << endl;
  for (vec_sz i = integers.size() / 2; i < integers.size() * 3 / 4; ++i)
    cout << integers[i] << endl;

  cout << "4th quartile" << endl;
  for (vec_sz i = integers.size() * 3 / 4; i < integers.size(); ++i)
    cout << integers[i] << endl;

  return 0;
}
