#pragma once

// `grade.h'
#include "Student_info.h"

double grade(double, double, double);
double grade(double, double, const Vec<double>&);
double grade(const Student_info&);
bool fgrade(const Student_info&);
