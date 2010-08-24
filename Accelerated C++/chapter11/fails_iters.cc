#include "Student_info.h"
#include "grade.h"

// version 3: iterators but no indexing; still potentially slow
Vec<Student_info> extract_fails(Vec<Student_info>& students) {
  Vec<Student_info> fail;
  Vec<Student_info>::iterator iter = students.begin();

  while (iter != students.end()) {
    if (fgrade(*iter)) {
      fail.push_back(*iter);
      iter = students.erase(iter);
    } 
    else ++iter;
  }

  return fail;
}
