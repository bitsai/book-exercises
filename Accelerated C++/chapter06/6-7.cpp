#include <vector>
#include <iostream>

#include "analysis.h"
#include "Student_info.h"

using std::cin;
using std::cout;
using std::endl;
using std::vector;

vector<Student_info> extract_didnt(vector<Student_info>& input) {
  vector<Student_info>::iterator iter =
    stable_partition(input.begin(), input.end(), did_all_hw);
  vector<Student_info> output(iter, input.end());
  input.erase(iter, input.end());

  return output;
}

int main() {
  // students who did and didn't do all their homework
  vector<Student_info> did;

  // read the student records and partition them
  Student_info student;
  while (read(cin, student))
    did.push_back(student);

  vector<Student_info> didnt = extract_didnt(did);

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
