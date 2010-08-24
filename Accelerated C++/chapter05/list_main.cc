#include <algorithm>
#include <list>
#include <string>

#include "grade.h"
#include "Student_info.h"

#ifdef _MSC_VER
#include "../minmax.h"
#else
using std::max;
#endif

using std::cin;
using std::cout;
using std::endl;
using std::list;
using std::string;

list<Student_info> extract_fails(list<Student_info>& v);

#ifdef _MSC_VER
// The definition of Student_info::operator< is necessary
// under Microsoft Visual C++ because the compiler does not
// support the ability to pass a comparison function as an
// argument to list::sort

bool operator<(const Student_info& x, const Student_info& y) {
  return compare(x, y);
}
#endif

int main() {
  list<Student_info> vs;
  Student_info s;
  string::size_type maxlen = 0;
  while (read(cin, s)) {
    maxlen = max(maxlen, s.name.size());
    vs.push_back(s);
  }

#ifdef _MSC_VER
  vs.sort();
#else
  vs.sort(compare);
#endif

  list<Student_info> fails = extract_fails(vs);

#ifdef _MSC_VER
  std::list<Student_info>::iterator i;
#else
  list<Student_info>::iterator i;
#endif

  for (i = fails.begin(); i != fails.end(); ++i)
    cout << i->name << " " << grade(*i) << endl;

  return 0;
}
