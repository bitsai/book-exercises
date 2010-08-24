#include <iostream>

#include "Student_info.h"
#include "Core.h"

using std::istream;

istream& Student_info::read(istream& is) {
  char ch;
  is >> ch;     // get record type

  // allocate new object of the appropriate type
  // use `Handle<T>(T*)' to build a `Handle<Core>' from the pointer to that object
  // call `Handle<T>::operator=' to assign the `Handle<Core>' to the left-hand side
  if (ch == 'U') cp = new Core(is);
  else cp = new Grad(is);

  return is;
}
