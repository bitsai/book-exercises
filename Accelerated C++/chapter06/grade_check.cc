#include <algorithm>

#include "Student_info.h"

using std::find;

bool did_all_hw(const Student_info& s) {
  return ((find(s.homework.begin(), s.homework.end(), 0))
	  == s.homework.end());
}
