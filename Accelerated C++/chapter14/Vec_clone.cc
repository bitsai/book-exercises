#include "Vec.h"

template <class T> T* clone(const T* tp);

template<> Vec<char>* clone(const Vec<char>* vp) {
  return new Vec<char>(*vp);
}
