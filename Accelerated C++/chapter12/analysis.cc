#include <algorithm>
#include <iostream>
#include <iterator>
#include <numeric>
#include <stdexcept>
#include "Vec.h"

#include "Student_info.h"
#include "grade.h"
#include "median.h"

using std::accumulate;           using std::back_inserter;
using std::domain_error;         using std::endl;
using std::ostream;              using std::remove_copy;
using std::transform;


double grade_aux(const Student_info& s)
{
	try {
		return grade(s);
	} catch (domain_error) {
		return grade(s.midterm, s.final, 0);
	}
}

// this version works fine
double median_analysis(const Vec<Student_info>& students)
{
	Vec<double> grades;

	transform(students.begin(), students.end(),
	          back_inserter(grades), grade_aux);
	return median(grades);
}

void write_analysis(ostream& out, const Str& name,
                    double analysis(const Vec<Student_info>&),
                    const Vec<Student_info>& did,
                    const Vec<Student_info>& didnt)
{
	out << name << ": median(did) = " << analysis(did) <<
	               ", median(didnt) = " << analysis(didnt) << endl;
}

double average(const Vec<double>& v)
{
	return accumulate(v.begin(), v.end(), 0.0) / v.size();
}

double average_grade(const Student_info& s)
{
	return grade(s.midterm, s.final, average(s.homework));
}

double average_analysis(const Vec<Student_info>& students)
{
	Vec<double> grades;

	transform(students.begin(), students.end(),
	          back_inserter(grades), average_grade);
	return median(grades);
}

// median of the nonzero elements of `s.homework', or `0' if no such elements exist
double optimistic_median(const Student_info& s)
{
	Vec<double> nonzero;
	remove_copy(s.homework.begin(), s.homework.end(),
	            back_inserter(nonzero), 0);

	if (nonzero.empty())
		return grade(s.midterm, s.final, 0);
	else
		return grade(s.midterm, s.final, median(nonzero));
}

double optimistic_median_analysis(const Vec<Student_info>& students)
{
        Vec<double> grades;

        transform(students.begin(), students.end(),
                  back_inserter(grades), optimistic_median);
        return median(grades);
}

