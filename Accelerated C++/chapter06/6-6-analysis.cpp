#include <algorithm>
#include <iostream>
#include <iterator>
#include <numeric>
#include <stdexcept>
#include <vector>

#include "6-6-analysis.h"
#include "Student_info.h"
#include "grade.h"
#include "median.h"

using std::accumulate;           using std::back_inserter;
using std::domain_error;         using std::endl;
using std::ostream;              using std::remove_copy;
using std::string;               using std::transform;
using std::vector;

double grade_aux(const Student_info& s) {
  try {
    return grade(s);
  } catch (domain_error) {
    return grade(s.midterm, s.final, 0);
  }
}

void write_analysis(ostream& out, const string& name,
                    double grading_function(const Student_info&),
                    const vector<Student_info>& did,
                    const vector<Student_info>& didnt) {
  out << name << ": median(did) = " << analysis(did, grading_function) 
      << ", median(didnt) = " << analysis(didnt, grading_function) << endl;
}

double average(const vector<double>& v) {
  return accumulate(v.begin(), v.end(), 0.0) / v.size();
}

double average_grade(const Student_info& s) {
  return grade(s.midterm, s.final, average(s.homework));
}

// median of the nonzero elements of `s.homework', or `0' if no such elements exist
double optimistic_median(const Student_info& s) {
  vector<double> nonzero;
  remove_copy(s.homework.begin(), s.homework.end(),
	      back_inserter(nonzero), 0);

  if (nonzero.empty())
    return grade(s.midterm, s.final, 0);
  else
    return grade(s.midterm, s.final, median(nonzero));
}

double analysis(const vector<Student_info>& students,
		double grading_function(const Student_info&)) {
  vector<double> grades;

  transform(students.begin(), students.end(),
	    back_inserter(grades), grading_function);

  return median(grades);
}
