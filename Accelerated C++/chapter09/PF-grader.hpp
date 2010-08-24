#pragma once

#include <vector>

#include "PF-Student_info.hpp"

class PF_grader {
public:
  PF_grader();
  void add_student(Student_info);
  void print_grades() const;

private:
  std::vector<Student_info> students;
};
