#include <iomanip>
#ifndef __GNUC__
#include <ios>
#endif
#include <iostream>
#include <string>
#include <vector>

using std::cin;                  using std::setprecision;
using std::cout;                 using std::string;
using std::endl;                 using std::streamsize;

#define NUM_HOMEWORK 2
using std::vector;

int main() {
  vector<string> names;
  vector<double> final_grades;
  bool done = false;

  while (!done) {
    // ask for and read the student's name
    cout << "Please enter your first name: ";
    string name;
    cin >> name;
    cout << "Hello, " << name << "!" << endl;
    names.push_back(name);

    // ask for and read the midterm and final grades
    cout << "Please enter your midterm and final exam grades: ";
    double midterm, final;
    cin >> midterm >> final;

    // ask for the homework grades
    cout << "Enter both your homework grades, "
      "followed by end-of-file: ";

    // the number and sum of grades read so far
    int count = 0;
    double sum = 0;

    // a variable into which to read
    double x;

    // invariant:
    //     we have read `count' grades so far, and
    //     `sum' is the sum of the first `count' grades
    while (count < NUM_HOMEWORK) {
      ++count;
      cin >> x;
      sum += x;
    }
  
    double final_grade = 0.2 * midterm + 0.4 * final + 0.4 * sum / count;
    final_grades.push_back(final_grade);

    cout << "More? (Y/N) ";
    string s;
    cin >> s;

    if (s != "Y")
      done = true;
  }

  for (vector<string>::size_type i = 0; i < names.size(); ++i) {
    // write the result
    streamsize prec = cout.precision();
    cout << names[i] << "'s final grade is " << setprecision(3)
       << final_grades[i]
       << setprecision(prec) << endl;
  }

  return 0;
}
