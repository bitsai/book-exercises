#ifndef GUARD_analysis_h
#define GUARD_analysis_h
#include <string>
#include <vector>

#include "Student_info.h"

bool did_all_hw(const Student_info& s);
double grade_aux(const Student_info& s);
double average_grade(const Student_info& s);
double optimistic_median(const Student_info& s);
double analysis(const std::vector<Student_info>& students,
		double grading_function(const Student_info&));
void write_analysis(std::ostream& out, const std::string& name,
                    double grading_function(const Student_info&),
                    const std::vector<Student_info>& did,
                    const std::vector<Student_info>& didnt);

#endif
