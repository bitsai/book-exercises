#ifndef GUARD_analysis_h
#define GUARD_analysis_h
#include <string>
#include <vector>

#include "Student_info.h"

bool did_all_hw(const Student_info& s);
double average_analysis(const std::vector<Student_info>& students);
double median_analysis(const std::vector<Student_info>& students);
double optimistic_median_analysis(const std::vector<Student_info>& students);
void write_analysis(std::ostream& out, const std::string& name,
                    double analysis(const std::vector<Student_info>&),
                    const std::vector<Student_info>& did,
                    const std::vector<Student_info>& didnt);

#endif
