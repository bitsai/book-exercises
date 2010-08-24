#ifndef GUARD_Student_info
#define GUARD_Student_info

// `Student_info.h' header file
#include <iostream>
#include "Str.h"
#include "Vec.h"

struct Student_info {
	Str name;
	double midterm, final;
	Vec<double> homework;
};

bool compare(const Student_info&, const Student_info&);
std::istream& read(std::istream&, Student_info&);
std::istream& read_hw(std::istream&, Vec<double>&);
#endif

