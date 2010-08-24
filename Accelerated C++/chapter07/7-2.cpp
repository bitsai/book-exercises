#include <algorithm>
#include <iomanip>
#include <iostream>
#include <stdexcept>
#include <map>
#include <string>
#include <vector>

using namespace std;

struct Student_info {
  string name;
  double midterm, final;
  vector<double> homework;
};	// note the semicolon--it's required

// compute the median of a `vector<double>'
// note that calling this function copies the entire argument `vector'
double median(vector<double> vec) {
  typedef vector<double>::size_type vec_sz;

  vec_sz size = vec.size();
  if (size == 0)
    throw domain_error("median of an empty vector");

  sort(vec.begin(), vec.end());

  vec_sz mid = size/2;

  return size % 2 == 0 ? (vec[mid] + vec[mid-1]) / 2 : vec[mid];
}

// compute a student's overall grade from midterm and final exam grades and homework grade
double grade(double midterm, double final, double homework) {
  return 0.2 * midterm + 0.4 * final + 0.4 * homework;
}

// compute a student's overall grade from midterm and final exam grades
// and vector of homework grades.
// this function does not copy its argument, because `median' does so for us.
double grade(double midterm, double final, const vector<double>& hw) {
  if (hw.size() == 0)
    throw domain_error("student has done no homework");

  return grade(midterm, final, median(hw));
}

double grade(const Student_info& s) {
  return grade(s.midterm, s.final, s.homework);
}

// read homework grades from an input stream into a `vector<double>'
istream& read_hw(istream& in, vector<double>& hw) {
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

istream& read(istream& is, Student_info& s) {
  // read and store the student's name and midterm and final exam grades
  is >> s.name >> s.midterm >> s.final;

  read_hw(is, s.homework);  // read and store all the student's homework grades

  return is;
}

bool compare(const Student_info& x, const Student_info& y) {
  return x.name < y.name;
}

string get_letter_grade(double grade) {
  if (grade >= 90.0)
    return "A";
  if (grade >= 80.0 && grade < 90.0)
    return "B";
  if (grade >= 70.0 && grade < 80.0)
    return "C";
  if (grade >= 60.0 && grade < 70.0)
    return "D";

  return "F";
}

int main() {
  vector<Student_info> students;
  Student_info record;
  string::size_type maxlen = 0;
  map<string, int> letter_grade_counts;

  // read and store all the records, and find the length of the longest name
  while (read(cin, record)) {
    maxlen = max(maxlen, record.name.size());
    students.push_back(record);
  }

  // alphabetize the records
  sort(students.begin(), students.end(), compare);
  
  for (vector<Student_info>::size_type i = 0;
       i != students.size(); ++i) {    
    // compute the grade
    try {
      double final_grade = grade(students[i]);
      letter_grade_counts[get_letter_grade(final_grade)]++;
    } catch (domain_error e) {
      cout << e.what();
    }
  }

  for (map<string, int>::const_iterator i = letter_grade_counts.begin();
       i != letter_grade_counts.end(); ++i)
    cout << i->first << ": " << i->second << endl;

  return 0;
}
