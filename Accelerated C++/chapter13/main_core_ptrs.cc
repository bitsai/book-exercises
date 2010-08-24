#include <vector>
#include <string>
#include <algorithm>
#include <iomanip>

#ifndef __GNUC__
#include <ios>
#endif

#include <iostream>
#include <stdexcept>

#include "Core.h"

using std::cout;
using std::cin;
using std::domain_error;
using std::endl;
using std::setprecision;
using std::setw;
using std::streamsize;
using std::sort;
using std::string;
using std::vector;

#ifdef _MSC_VER
#include "../minmax.h"
#else
using std::max;
#endif

// this code almost works; see 13.3.2/242
int main()
{
	vector<Core*> students;         // store pointers, not objects
	Core* record;                   // temporary must be a pointer as well
	char ch;
	string::size_type maxlen = 0;

	// read and store the data
	while (cin >> ch) {
		if (ch == 'U')
			record = new Core;      // allocate a `Core' object
		else
			record = new Grad;      // allocate a `Grad' object
		record->read(cin);          // `virtual' call
		maxlen = max(maxlen, record->name().size());// dereference
		students.push_back(record);
	}

	// pass the version of `compare' that works on pointers
	sort(students.begin(), students.end(), compare_Core_ptrs);

	// write the names and grades
#ifdef _MSC_VER
	for (std::vector<Core*>::size_type i = 0;
#else
	for (vector<Core*>::size_type i = 0;
#endif
	     i != students.size(); ++i) {
		// `students[i]' is a pointer that we dereference to call the functions
		cout << students[i]->name()
		     << string(maxlen + 1 - students[i]->name().size(), ' ');
		try {
			double final_grade = students[i]->grade();
			streamsize prec = cout.precision();
			cout << setprecision(3) << final_grade
			     << setprecision(prec) << endl;

		} catch (domain_error e) {
			cout << e.what() << endl;
		}
		delete students[i];        // free the object allocated when reading
	}
	return 0;
}

