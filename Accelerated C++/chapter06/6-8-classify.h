#pragma once

#include <vector>

#include "Student_info.h"

std::vector<Student_info> classify(std::vector<Student_info>&, 
				   bool criteria(const Student_info&));
