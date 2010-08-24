#pragma once

#include <algorithm>
#include <iostream>
#include <string>
#include <vector>

#include "grade.h"

class Core {
  friend class Student_info;

 public:
 Core(): midterm(0), final(0) { }
  Core(std::istream& is) { read(is); }

  std::string name() const { return n; }

  virtual std::istream& read(std::istream&);
  virtual double grade() const { return ::grade(midterm, final, homework); }
  virtual bool valid() const { return !homework.empty(); }
  virtual bool fulfill_reqs() const {
    return (std::find(homework.begin(), homework.end(), 0.0)
	    == homework.end());
  }

  virtual ~Core() { }

 protected:
  std::string n;
  double midterm, final;
  std::vector<double> homework;

  std::istream& read_common(std::istream&);

  virtual Core* clone() const { return new Core(*this); }
};

class Grad: public Core {
 public:
 Grad(): thesis(0) { }
  Grad(std::istream& is) { read(is); }

  std::istream& read(std::istream&);
  double grade() const { return std::min(Core::grade(), thesis); }
  bool fulfill_reqs() const { return (thesis > 0.0); }

 private:
  double thesis;

  Grad* clone() const { return new Grad(*this); }
};

class PassFail: public Core {
 public:
  PassFail() { }
  PassFail(std::istream& is) { Core::read(is); }

  double grade() const {
    if (homework.size() > 0) return ::grade(midterm, final, homework);
    else return (midterm + final) / 2;
  }

  bool valid() const { return true; }  
  bool fulfill_reqs() const { return true; }

 private:
  PassFail* clone() const { return new PassFail(*this); }
};

class Audit: public Core {
 public:
  Audit() { }
  Audit(std::istream& is) { read(is); }

  std::istream& read(std::istream&);
  double grade() const { return 0.0; }
  bool valid() const { return true; }  
  bool fulfill_reqs() const { return true; }

 private:
  Audit* clone() const { return new Audit(*this); }
};

bool compare(const Core&, const Core&);
bool compare_Core_ptrs(const Core*, const Core*);
std::string letter_grade(double);
