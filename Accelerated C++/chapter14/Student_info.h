#pragma once

#include <iostream>
#include <string>

#include "Core.h"
#include "Ptr.h"

class Student_info {
 public:
  Student_info() { }
  Student_info(std::istream& is) { read(is); }
  // no copy, assign, or destructor: they're no longer needed

  std::istream& read(std::istream&);

  std::string name() const {
    if (cp) return cp->name();
    else throw std::runtime_error("uninitialized Student");
  }

  double grade() const {
    if (cp) return cp->grade();
    else throw std::runtime_error("uninitialized Student");
  }

  static bool compare(const Student_info& s1, const Student_info& s2) {
    return s1.name() < s2.name();
  }

 private:
  Ptr<Core> cp;
};
