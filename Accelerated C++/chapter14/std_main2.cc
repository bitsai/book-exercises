#include <algorithm>
#include <iomanip>
#include <iostream>
#include <stdexcept>
#include <vector>

#include "Ref_handle.h"
#include "Student_info.h"

using namespace std;

bool compare_Core_handles(const Ref_handle<Core>& lhs, const Ref_handle<Core>& rhs) {
  return compare(*lhs, *rhs);
}

int main() {
  vector< Ref_handle<Core> > students;       // changed type
  Ref_handle<Core> record;                   // changed type
  char ch;
  string::size_type maxlen = 0;

  // read and store the data
  while (cin >> ch) {
    if (ch == 'U') record = new Core;      // allocate a `Core' object
    else record = new Grad;      // allocate a `Grad' object

    record->read(cin);  //  `Ref_handle<T>::->', then `virtual' call to `read'
    maxlen = max(maxlen, record->name().size()); // `Ref_handle<T>::->'
    students.push_back(record);
  }

  // `compare' must be rewritten to work on `const Ref_handle<Core>&'
  sort(students.begin(), students.end(), compare_Core_handles);

  // write the names and grades
  for (vector< Ref_handle<Core> >::size_type i = 0; i != students.size(); ++i) {
    // `students[i]' is a `Ref_handle', which we dereference to call the functions
    cout << students[i]->name()
	 << string(maxlen + 1 - students[i]->name().size(), ' ');

    try {
      double final_grade = students[i]->grade();
      streamsize prec = cout.precision();
      cout << setprecision(3) << final_grade << setprecision(prec) << endl;
    } catch (domain_error e) {
      cout << e.what() << endl;
    }
    // no `delete' statement
  }

  return 0;
}
