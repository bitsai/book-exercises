#include <algorithm>
#include <iomanip>
#ifndef __GNUC__
#include <ios>
#endif
#include <iostream>
#include <string>
#include <vector>

using std::cin;             using std::sort;
using std::cout;            using std::streamsize;
using std::endl;            using std::string;
using std::setprecision;    using std::vector;

int main() {
  // ask for and read the student's name
  cout << "Please enter your first name: ";
  string name;
  cin >> name;
  cout << "Hello, " << name << "!" << endl;

  // ask for and read the midterm and final grades
  cout << "Please enter your midterm and final exam grades: ";
  double midterm, final;
  cin >> midterm >> final;

  // ask for and read the homework grades
  cout << "Enter all your homework grades, "
    "followed by end-of-file: ";

  vector<double> homework;
  double x;
  // invariant: `homework' contains all the homework grades read so far
  while (cin >> x)
    homework.push_back(x);

  // check that the student entered some homework grades
#ifdef _MSC_VER
  typedef std::vector<double>::size_type vec_sz;
#else
  typedef vector<double>::size_type vec_sz;
#endif
  vec_sz size = homework.size();
  if (size == 0) {
    cout << endl << "You must enter your grades.  "
      "Please try again." << endl;	
    return 1;
  }

  // sort the grades
  sort(homework.begin(), homework.end());

  // compute the median homework grade
  vec_sz mid = size/2;
  double median;
  median = size % 2 == 0 ? (homework[mid] + homework[mid-1]) / 2
    : homework[mid];

  // compute and write the final grade
  streamsize prec = cout.precision();
  cout << "Your final grade is " << setprecision(3)
       << 0.2 * midterm + 0.4 * final + 0.4 * median
       << setprecision(prec) << endl;

  return 0;
}
