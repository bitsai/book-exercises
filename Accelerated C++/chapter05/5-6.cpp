#include <algorithm>
#include <vector>
#include <iostream>
#include <string>
#include <ctime>

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

vector<Student_info> extract_fails(vector<Student_info>& students) {
  vector<Student_info> fail;
#ifdef _MSC_VER
  std::vector<Student_info>::size_type i = 0;
  std::vector<Student_info>::size_type rawsize = students.size();
#else
  vector<Student_info>::size_type i = 0;
  vector<Student_info>::size_type rawsize = students.size();
#endif

  // invariant: elements `[0,' `i)' of `students' represent passing grades
  while (i != students.size()) {
    if (fgrade(students[i])) {
      fail.push_back(students[i]);
    } else {
      students.insert(students.begin(), students[i]);
      ++i;
    }
    ++i;
  }
    
  students.resize(rawsize - fail.size());

  return fail;
}

int main() {
  vector<Student_info> vs;
  Student_info s;
  string::size_type maxlen = 0;
  while (read(cin, s)) {
    maxlen = max(maxlen, s.name.size());
    vs.push_back(s);
  }

  //  sort(vs.begin(), vs.end(), compare);

  clock_t start = clock();
  vector<Student_info> fails = extract_fails(vs);
  clock_t elapsed = clock() - start;

  cout << "Elapsed: " << elapsed << endl;

  for (int i = 0; i < vs.size(); ++i)
    cout << vs[i].name << " " << grade(vs[i]) << endl;

  //  for (int i = 0; i < fails.size(); ++i)
  //    cout << fails[i].name << " " << grade(fails[i]) << endl;

  return 0;
}
