#include "Pic.h"

using namespace std;

int main() {
  Vec<Str> xs;
  xs.push_back("AAAA");
  xs.push_back("AA");
  xs.push_back("AA");
  xs.push_back("AAAA");

  Vec<Str> ys;
  ys.push_back("BB");
  ys.push_back("BB");

  Picture p(xs);
  Picture q(ys);

  cout << hcat(p, q) << endl;
  cout << hcat(q, p) << endl;

  return 0;
}
