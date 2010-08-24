#pragma once

#include <algorithm>
#include <stdexcept>
#include "Vec.h"

using std::domain_error;
using std::sort;

template <class T>
T median(Vec<T> v) {
  typedef typename Vec<T>::size_type vec_sz;
  vec_sz size = v.size();
  if (size == 0) throw domain_error("median of an empty Vec");
  sort(v.begin(), v.end());
  vec_sz mid = size / 2;
  return size % 2 == 0 ? (v[mid] + v[mid - 1]) / 2 : v[mid];
}
