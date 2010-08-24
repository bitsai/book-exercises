#include <vector>
#include <iostream>

#include "6-8-classify.h"
#include "analysis.h"
#include "Student_info.h"

using std::cin;
using std::cout;
using std::endl;
using std::vector;

int main() {
  // students who did and didn't do all their homework
  vector<Student_info> did;

  // read the student records and partition them
  Student_info student;
  while (read(cin, student))
    did.push_back(student);

  vector<Student_info> didnt = classify(did, did_all_hw);

  // verify that the analyses will show us something
  if (did.empty()) {
    cout << "No student did all the homework!" << endl;
    return 1;
  }

  if (didnt.empty()) {
    cout << "Every student did all the homework!" << endl;
    return 1;
  }

  // do the analyses
  write_analysis(cout, "median", median_analysis, did, didnt);
  write_analysis(cout, "average", average_analysis, did, didnt);
  write_analysis(cout, "median of homework turned in",
		 optimistic_median_analysis, did, didnt);

  return 0;
}
