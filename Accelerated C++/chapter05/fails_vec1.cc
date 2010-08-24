#include <vector>
#include "Student_info.h"
#include "grade.h"

using std::vector;

// separate passing and failing student records: first try
vector<Student_info> extract_fails(vector<Student_info>& students) {
  vector<Student_info> pass, fail;

#ifdef _MSC_VER
  for (std::vector<Student_info>::size_type i = 0;
#else
       for (vector<Student_info>::size_type i = 0;
#endif
	    i != students.size(); ++i)
	 if (fgrade(students[i]))
	   fail.push_back(students[i]);
	 else
	   pass.push_back(students[i]);

       students = pass;

       return fail;
       }
