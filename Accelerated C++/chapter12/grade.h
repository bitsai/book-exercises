#ifndef GUARD_grade_h
#define GUARD_grade_h

// `grade.h'
#include "Vec.h"
#include "Student_info.h"

double grade(double, double, double);
double grade(double, double, const Vec<double>&);
double grade(const Student_info&);

#endif

