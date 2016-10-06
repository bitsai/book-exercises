#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

#ifdef _MSC_VER
  typedef std::vector<int>::size_type vec_sz;
#else
  typedef vector<int>::size_type vec_sz;
#endif

double median(const vector<int>& integers, vec_sz begin, vec_sz end) {
  vec_sz size = begin + end;
  vec_sz mid = size / 2;
  return size % 2 == 0 ?
    (integers[mid] + integers[mid - 1]) / 2.0 :
    integers[mid];
}

int main() {
  vector<int> integers;
  int x;

  cout << "Integers: ";

  while (cin >> x)
    integers.push_back(x);

  if (integers.size() == 0) {
    cout << endl << "No integers!" << endl;
    return 1;
  }

  sort(integers.begin(), integers.end());

  vec_sz mid = integers.size() / 2;
  vec_sz lower_half_end;
  vec_sz upper_half_begin;

  // compute quartiles using Method 1 from: https://en.wikipedia.org/wiki/Quartile
  if (integers.size() % 2 != 0) {
    // odd number of data points, exclude median from either half
    lower_half_end = mid;
    upper_half_begin = mid + 1;
  } else {
    // even number of data points, split data set exactly in half
    lower_half_end = mid;
    upper_half_begin = mid;
  }

  cout << "Q1" << endl;
  cout << median(integers, 0, lower_half_end) << endl;

  cout << "Q2" << endl;
  cout << median(integers, 0, integers.size()) << endl;

  cout << "Q3" << endl;
  cout << median(integers, upper_half_begin, integers.size()) << endl;

  return 0;
}
