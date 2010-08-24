#include <list>
#include "Student_info.h"
#include "grade.h"

using std::list;

// version 4: use `list' instead of `vector'
list<Student_info> extract_fails(list<Student_info>& students) {
  list<Student_info> fail;
#ifdef _MSC_VER
  std::list<Student_info>::iterator iter = students.begin();
#else
  list<Student_info>::iterator iter = students.begin();
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
