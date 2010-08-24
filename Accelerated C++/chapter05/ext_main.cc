#include <algorithm>
#include <vector>
#include <iostream>
#include <string>

#include "Student_info.h"
#include "grade.h"

//driver program for grade partitioning examples

using std::cin;
using std::cout;
using std::endl;
using std::sort;
using std::string;
using std::vector;

#ifdef _MSC_VER
#include "../minmax.h"
#else
using std::max;
#endif

vector<Student_info> extract_fails(vector<Student_info>& v);

int main() {
  vector<Student_info> vs;
  Student_info s;
  string::size_type maxlen = 0;
  while (read(cin, s)) {
    maxlen = max(maxlen, s.name.size());
    vs.push_back(s);
  }

  sort(vs.begin(), vs.end(), compare);

  vector<Student_info> fails = extract_fails(vs);

  for (int i = 0; i < fails.size(); ++i)
    cout << fails[i].name << " " << grade(fails[i]) << endl;

  return 0;
}
