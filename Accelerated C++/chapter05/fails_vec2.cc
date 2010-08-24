#include <vector>
#include "Student_info.h"
#include "grade.h"

using std::vector;

// second try: correct but potentially slow
vector<Student_info> extract_fails(vector<Student_info>& students) {
  vector<Student_info> fail;
#ifdef _MSC_VER
  std::vector<Student_info>::size_type i = 0;
#else
  vector<Student_info>::size_type i = 0;
#endif

  // invariant: elements `[0,' `i)' of `students' represent passing grades
  while (i != students.size()) {
    if (fgrade(students[i])) {
      fail.push_back(students[i]);
      students.erase(students.begin() + i);
    } else
      ++i;
  }

  return fail;
}
