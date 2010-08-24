#include <iostream>
#include <vector>

#include "PF-Student_info.hpp"

using namespace std;

Student_info::Student_info(): midterm(0), final(0) { }

Student_info::Student_info(istream& is) { read(is); }	

istream& Student_info::read(istream& in) {
  in >> n >> midterm >> final;
  return in;
}

bool Student_info::passing() const {
  return ((midterm + final) / 2) > 60.0;
}

bool compare(const Student_info& x, const Student_info& y) {
  return x.name() < y.name();
}
