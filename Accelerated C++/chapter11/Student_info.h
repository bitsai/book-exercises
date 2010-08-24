#pragma once

// `Student_info.h' header file
#include <iostream>
#include <string>

#include "Vec.h"

struct Student_info {
public:
  std::string name;
  double midterm, final;
  Vec<double> homework;

  Student_info() { print("create"); }

  Student_info(const Student_info& other) {
    print("copy");
    clone(other);
  }

  Student_info& operator=(const Student_info& other) {
    print("assign");
    if (this != &other) clone(other);
    return *this;
  }

  ~Student_info() { print("destroy"); }

private:
  void print(const std::string&);
  void clone(const Student_info& other) {
    name = other.name;
    midterm = other.midterm;
    final = other.final;
    homework = other.homework;
  }
};

bool compare(const Student_info&, const Student_info&);
std::istream& read(std::istream&, Student_info&);
std::istream& read_hw(std::istream&, Vec<double>&);
