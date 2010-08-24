#include <algorithm>
#include <iterator>
#include <vector>

using namespace std;

int main() {
  vector<int> u(10, 100);
  vector<int> v;
  copy(u.begin(), u.end(), back_inserter(v));
  //  copy(u.begin(), u.end(), inserter(v, v.begin()));

  return 0;
}
