#pragma once

#include <string>
#include <vector>

class Student_info {
 public:
  Student_info();              // construct an empty `Student_info' object
  Student_info(std::istream&); // construct one by reading a stream
  std::string name() const { return n; }

  // as defined in 9.2.1/157, and changed to read into `n' instead of `name'
  std::istream& read(std::istream&);

  bool passing() const;

 private:
  std::string n;
  double midterm, final;
};

bool compare(const Student_info&, const Student_info&);
