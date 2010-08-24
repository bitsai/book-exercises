#include "Pic.h"

using namespace std;

int main() {
  Vec<Str> vs;
  vs.push_back("hello");
  vs.push_back("world");

  Picture p(vs);
  Picture q = frame(p);
  Picture r = hcat(p, q);
  Picture s = vcat(q, r);

  cout << frame(hcat(s, vcat(r, q))) << endl;

  return 0;
}
