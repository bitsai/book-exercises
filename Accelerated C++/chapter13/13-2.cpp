#include <stdexcept>

#include "Core.h"

using namespace std;

int main() {
  Core* p1 = new Core;
  Core* p2 = new Grad;
  Core s1;
  Grad s2;

  try {
    p1->grade();
  }
  catch (domain_error e) { }
  p1->name();

  try {
    p2->grade();
  }
  catch (domain_error e) { }
  p2->name();

  try {
    s1.grade();
  }
  catch (domain_error e) { }
  s1.name();

  try {
    s2.grade();
  }
  catch (domain_error e) { }
  s2.name();

  return 0;
}
