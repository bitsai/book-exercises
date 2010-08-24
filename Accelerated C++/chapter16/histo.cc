#include <algorithm>
#include <iostream>

#include "Core.h"
#include "Pic.h"
#include "Student_info.h"

using std::cin;
using std::cout;
using std::endl;
using std::sort;
using std::string;
using std::vector;

Picture histogram(const vector<Student_info>& students)
{
	Picture names;
	Picture grades;

	// for each student
#ifdef _MSC_VER
	for (std::vector<Student_info>::const_iterator it = students.begin();
#else
	for (vector<Student_info>::const_iterator it = students.begin();
#endif
	     it != students.end(); ++it) {

		// create vertically concatenated pictures of the names and grades
		names = vcat(names, vector<string>(1, it->name()));
		grades = vcat(grades,
#ifdef __BORLANDC__
		    vector<string>(1, " " + string(static_cast<size_t>(it->grade() / 5), '=')));
#else
		    vector<string>(1, " " + string(it->grade() / 5, '=')));
#endif
	}

	// horizontally concatenate the name and grade pictures to combine them
	return hcat(names, grades);
}

int main()
{
	vector<Student_info> students;
	Student_info s;

	// read the names and grades
	while (s.read(cin))
		students.push_back(s);

	// put the students in alphabetical order
	sort(students.begin(), students.end(), Student_info::compare);

	// write the names and histograms
	cout << frame(histogram(students)) << endl;
	return 0;
}

