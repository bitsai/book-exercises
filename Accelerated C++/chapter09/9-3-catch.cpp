#include <iostream>
#include <stdexcept>

#include "Student_info.h"

using namespace std;

int main() {
  try {
    Student_info record;
    record.grade();
  } catch (domain_error e) {
    cout << e.what() << endl;
  }

  return 0;
}
