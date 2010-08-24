#include <iostream>
#include <numeric>
#include <vector>

using namespace std;

int main() {
  vector<double> nums;
  double num;

  while (cin >> num)
    nums.push_back(num);

  cout << accumulate(nums.begin(), nums.end(), 0.0) / nums.size() << endl;
}
