#ifndef GUARD_analysis_h
#define GUARD_analysis_h
#include <string>
#include "Vec.h"

#include "Student_info.h"

bool did_all_hw(const Student_info& s);
double average_analysis(const Vec<Student_info>& students);
double median_analysis(const Vec<Student_info>& students);
double optimistic_median_analysis(const Vec<Student_info>& students);
void write_analysis(std::ostream& out, const std::string& name,
                    double analysis(const Vec<Student_info>&),
                    const Vec<Student_info>& did,
                    const Vec<Student_info>& didnt);

#endif
