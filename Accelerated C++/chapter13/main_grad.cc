#include <algorithm>

#ifndef __GNUC__
#include <ios>
#endif

#include <iostream>
#include <iomanip>
#include <stdexcept>
#include <string>
#include <vector>

#include "Core.h"

using std::cout;
using std::cin;
using std::domain_error;
using std::endl;
using std::setprecision;
using std::setw;
using std::sort;
using std::streamsize;
using std::string;
using std::vector;

#ifdef _MSC_VER
#include "../minmax.h"
#else
using std::max;
#endif

int main()
{
	vector<Grad> students;          // different type in the `vector'
	Grad record;                    // different type into which to `read'
	string::size_type maxlen = 0;

	// read and store the data
	while (record.read(cin)) {      // `read' from `Grad', not `Core'
		maxlen = max(maxlen, record.name().size());
		students.push_back(record);
	}

	// alphabetize the student records
	sort(students.begin(), students.end(), compare);

	// write the names and grades
#ifdef _MSC_VER
	for (std::vector<Grad>::size_type i = 0; i != students.size(); ++i) {
#else
	for (vector<Grad>::size_type i = 0; i != students.size(); ++i) {
#endif
		cout << students[i].name()
		     << string(maxlen + 1 - students[i].name().size(), ' ');
		try {
			double final_grade = students[i].grade(); // `Grad::grade'
			streamsize prec = cout.precision();
			cout << setprecision(3) << final_grade
			     << setprecision(prec) << endl;
		} catch (domain_error e) {
			cout << e.what() << endl;
		}
	}
	return 0;
}

