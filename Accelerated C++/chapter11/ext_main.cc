#include <algorithm>
#include <iostream>
#include <string>

#include "Student_info.h"
#include "grade.h"

//driver program for grade partitioning examples

using namespace std;

Vec<Student_info> extract_fails(Vec<Student_info>& v);

int main() {
  Vec<Student_info> vs;
  Student_info s;
  string::size_type maxlen = 0;

  while (read(cin, s)) {
    maxlen = max(maxlen, s.name.size());
    vs.push_back(s);
  }

  sort(vs.begin(), vs.end(), compare);

  Vec<Student_info> fails = extract_fails(vs);

  for (int i = 0; i < fails.size(); ++i)
    cout << fails[i].name << " " << grade(fails[i]) << endl;

  return 0;
}
