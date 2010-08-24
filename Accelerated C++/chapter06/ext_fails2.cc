#include <algorithm>
#include <vector>

#include "Student_info.h"
#include "grade.h"

using std::stable_partition;
using std::vector;

vector<Student_info> extract_fails(vector<Student_info>& students) {
#ifdef _MSC_VER
  std::vector<Student_info>::iterator iter =
#else
    vector<Student_info>::iterator iter =
#endif
    stable_partition(students.begin(), students.end(), pgrade);
  vector<Student_info> fail(iter, students.end());
  students.erase(iter, students.end());

  return fail;
}
