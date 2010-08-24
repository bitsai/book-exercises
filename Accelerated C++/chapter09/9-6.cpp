#include <algorithm>
#include <iostream>

#include "PF-grader.hpp"

using namespace std;

PF_grader::PF_grader() { }

bool passing(const Student_info& s) {
  return s.passing();
}

void PF_grader::add_student(Student_info student) {
  students.push_back(student);
  partition(students.begin(), students.end(), passing);
}

void PF_grader::print_grades() const {
  for (vector<Student_info>::const_iterator i = students.begin();
       i != students.end(); ++i) {
    string pf = (i->passing()) ? "P" : "F";
    cout << i->name() << ": " << pf << endl;
  }
}

int main() {
  PF_grader pf_grader;
  Student_info student;

  while (student.read(cin)) pf_grader.add_student(student);
  pf_grader.print_grades();

  return 0;
}
