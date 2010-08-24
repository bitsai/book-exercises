#include <iostream>
#include <vector>

#include "10-2.hpp"

using namespace std;

int main() {
  int test_arr[] = {1, 2, 8, 4, 5, 6, 7, 3, 9, 10};
  vector<int> test_vec(test_arr, test_arr + 10);

  cout << "Median: " << median<int>(test_arr, test_arr + 10) << endl;

  for (int i = 0; i < 10; ++i)
    cout << test_arr[i] << endl;;

  cout << "Median: " << median<int>(test_vec.begin(), test_vec.end()) << endl;

  for (int i = 0; i < 10; ++i)
    cout << test_vec[i] << endl;

  return 0;
}
