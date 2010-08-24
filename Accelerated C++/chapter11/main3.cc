#include <algorithm>
#include <iomanip>
#ifndef __GNUC__
#include <ios>
#endif
#include <iostream>
#include <stdexcept>
#include <string>
#include "Vec.h"
#include "grade.h"
#include "Student_info.h"

using std::cin;                     using std::setprecision;
using std::cout;                    using std::sort;
using std::domain_error;            using std::streamsize;
using std::endl;                    using std::string;
#ifndef _MSC_VER
using std::max;
#else


#include "../minmax.h"
#endif

int main()
{
  Vec<Student_info> students;
  Student_info record;
  string::size_type maxlen = 0;       // the length of the longest name

  // read and store all the students' data.
  // Invariant:	`students' contains all the student records read so far
  //			`maxlen' contains the length of the longest name in `students'
  while (read(cin, record)) {
    // find length of longest name
    maxlen = max(maxlen, record.name.size());
    students.push_back(record);
  }

  // alphabetize the student records
  sort(students.begin(), students.end(), compare);

  // write the names and grades
#ifdef _MSC_VER
  for (Vec<Student_info>::size_type i = 0;
#else
       for (Vec<Student_info>::size_type i = 0;
#endif
	    i != students.size(); ++i) {

	 // write the name, padded on the right to `maxlen' `+' `1' characters
	 cout << students[i].name
	      << string(maxlen + 1 - students[i].name.size(), ' ');

	 // compute and write the grade
	 try {
	   double final_grade = grade(students[i]);
	   streamsize prec = cout.precision();
	   cout << setprecision(3) << final_grade
		<< setprecision(prec);
	 } catch (domain_error e) {
	   cout << e.what();
	 }
	 cout << endl;
       }
	 return 0;
       }

