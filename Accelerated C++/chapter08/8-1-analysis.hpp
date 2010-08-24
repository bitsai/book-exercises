#pragma once

#include <algorithm>
#include <iostream>
#include <iterator>
#include <string>
#include <vector>

#include "median.h"
#include "Student_info.h"

bool did_all_hw(const Student_info& s);
double grade_aux(const Student_info& s);
double average_grade(const Student_info& s);
double optimistic_median(const Student_info& s);

template <class Function>
double analysis(const std::vector<Student_info>& students,
		Function grading_function) {
  std::vector<double> grades;

  transform(students.begin(), students.end(),
	    back_inserter(grades), grading_function);

  return median(grades);
}

template <class Function>
void write_analysis(std::ostream& out, const std::string& name,
                    Function grading_function,
                    const std::vector<Student_info>& did,
                    const std::vector<Student_info>& didnt) {
  out << name << ": median(did) = " << analysis(did, grading_function) 
      << ", median(didnt) = " << analysis(didnt, grading_function) << std::endl;
}
