#include <algorithm>
#include <iomanip>
#include <iostream>
#include <stdexcept>
#include <string>
#include "Vec.h"

#ifdef _MSC_VER
#include "../minmax.h"
#else
using std::max;
#endif

using std::cin;
using std::cout;
using std::domain_error;
using std::endl;
using std::istream;
using std::ostream;
using std::setprecision;
using std::setw;
using std::sort;
using std::streamsize;
using std::string;


struct Student_info {
  string name;
  double midterm, final;
  Vec<double> homework;
};	// note the semicolon--it's required

// compute the median of a `Vec<double>'
// note that calling this function copies the entire argument `Vec'
double median(Vec<double> vec)
{
#ifdef _MSC_VER
  typedef Vec<double>::size_type vec_sz;
#else
  typedef Vec<double>::size_type vec_sz;
#endif

  vec_sz size = vec.size();
  if (size == 0)
    throw domain_error("median of an empty Vec");

  sort(vec.begin(), vec.end());

  vec_sz mid = size/2;

  return size % 2 == 0 ? (vec[mid] + vec[mid-1]) / 2 : vec[mid];
}

// compute a student's overall grade from midterm and final exam grades and homework grade
double grade(double midterm, double final, double homework)
{
  return 0.2 * midterm + 0.4 * final + 0.4 * homework;
}

// compute a student's overall grade from midterm and final exam grades
// and Vec of homework grades.
// this function does not copy its argument, because `median' does so for us.
double grade(double midterm, double final, const Vec<double>& hw)
{
  if (hw.size() == 0)
    throw domain_error("student has done no homework");
  return grade(midterm, final, median(hw));
}

double grade(const Student_info& s)
{
  return grade(s.midterm, s.final, s.homework);
}

// read homework grades from an input stream into a `Vec<double>'
istream& read_hw(istream& in, Vec<double>& hw)
{
  if (in) {
    // get rid of previous contents
    hw.clear();

    // read homework grades
    double x;
    while (in >> x)
      hw.push_back(x);

    // clear the stream so that input will work for the next student
    in.clear();
  }
  return in;
}

istream& read(istream& is, Student_info& s)
{
  // read and store the student's name and midterm and final exam grades
  is >> s.name >> s.midterm >> s.final;

  read_hw(is, s.homework);  // read and store all the student's homework grades
  return is;
}

bool compare(const Student_info& x, const Student_info& y)
{
  return x.name < y.name;
}

int main()
{
  Vec<Student_info> students;
  Student_info record;
  string::size_type maxlen = 0;

  // read and store all the records, and find the length of the longest name
  while (read(cin, record)) {
    maxlen = max(maxlen, record.name.size());
    students.push_back(record);
  }

  // alphabetize the records
  sort(students.begin(), students.end(), compare);

#ifdef _MSC_VER
  for (Vec<Student_info>::size_type i = 0;
#else
       for (Vec<Student_info>::size_type i = 0;
#endif
	    i != students.size(); ++i) {

	 // write the name, padded on the right to `maxlen' `+' `1' characters
	 cout << students[i].name
	      << string(maxlen + 1 - students[i].name.size(), ' ');

	 // compute and write the grade
	 try {
	   double final_grade = grade(students[i]);
	   streamsize prec = cout.precision();
	   cout << setprecision(3) << final_grade
		<< setprecision(prec);
	 } catch (domain_error e) {
	   cout << e.what();
	 }

	 cout << endl;
       }

	 return 0;
       }

