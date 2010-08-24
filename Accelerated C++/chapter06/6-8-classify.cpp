#include <algorithm>

#include "6-8-classify.h"

using namespace std;

vector<Student_info> classify(vector<Student_info>& input, 
			      bool criteria(const Student_info&)) {
  vector<Student_info>::iterator iter =
    stable_partition(input.begin(), input.end(), criteria);
  vector<Student_info> output(iter, input.end());
  input.erase(iter, input.end());

  return output;
}
