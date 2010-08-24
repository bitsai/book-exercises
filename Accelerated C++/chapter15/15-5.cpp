#include "Pic.h"

using namespace std;

int main() {
  Vec<Str> vs;
  vs.push_back("hello");
  vs.push_back("world");
  vs.push_back("!");

  Picture p(vs);
  Picture q = frame(p);

  cout << hcat(hcat(p, q), hcat(q, p)) << endl;

  return 0;
}
