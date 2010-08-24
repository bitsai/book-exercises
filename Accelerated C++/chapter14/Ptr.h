#pragma once

#include <cstddef>
#include <stdexcept>

#include "Ref_count.hpp"

template <class T> class Ptr {
 public:
  void make_unique() {
    if (refptr.val() != 1) {
      --refptr;
      refptr = Ref_count();
      p = p? clone(p): 0;
    }
  }

 Ptr(): p(0) { }
 Ptr(T* t): p(t) { }
 Ptr(const Ptr& h): p(h.p), refptr(h.refptr) { ++refptr; }

  Ptr& operator=(const Ptr&);
  ~Ptr();
  operator bool() const { return p; }
  T& operator*() const;
  T* operator->() const;

 private:
  T* p;
  Ref_count refptr;
};

template <class T> T* clone(const T* tp) {
  return tp->clone();
}

template<class T> T& Ptr<T>::operator*() const { 
  if (p) return *p; throw std::runtime_error("unbound Ptr"); 
}

template<class T> T* Ptr<T>::operator->() const { 
  if (p) return p; throw std::runtime_error("unbound Ptr"); 
}

template<class T> Ptr<T>& Ptr<T>::operator=(const Ptr& rhs) {
  ++rhs.refptr;
  --refptr;

  if (refptr.val() == 0) {
    delete p;
  }

  refptr = rhs.refptr;
  p = rhs.p;

  return *this;
}

  template<class T> Ptr<T>::~Ptr() {
    --refptr;

    if (refptr.val() == 0) {
      delete p;
    }
  }
