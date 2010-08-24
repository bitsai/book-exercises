#include <vector>
#include "Student_info.h"
#include "grade.h"

using std::vector;

// version 3: iterators but no indexing; still potentially slow
vector<Student_info> extract_fails(vector<Student_info>& students) {
  vector<Student_info> fail;
#ifdef _MSC_VER
  std::vector<Student_info>::iterator iter = students.begin();
#else
  vector<Student_info>::iterator iter = students.begin();
#endif

  while (iter != students.end()) {
    if (fgrade(*iter)) {
      fail.push_back(*iter);
      iter = students.erase(iter);
    } else
      ++iter;
  }

  return fail;
}
