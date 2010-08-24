#pragma once

#include <iterator>

template <class Type>
void my_swap(Type& a, Type& b) {
  Type c = a;
  a = b;
  b = c;
}
