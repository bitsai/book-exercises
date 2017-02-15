#include "Pic.h"

using namespace std;

int main() {
  Vec<Str> xs;
  xs.push_back("1111");
  xs.push_back("2222");
  xs.push_back("3333");
  xs.push_back("4444");

  Vec<Str> ys;
  ys.push_back("AA");
  ys.push_back("BB");

  Picture p(xs);
  Picture q(ys);

  cout << hcat(p, q) << endl;
  cout << hcat(q, p) << endl;

  return 0;
}
