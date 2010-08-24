#pragma once

#include <cstdarg>
#include <memory>

template <class T> class list {
  template <class U> friend list<U> cons(const U&, const list<U>&);
  template <class U> friend U car(const list<U>&);
  template <class U> friend list<U> cdr(const list<U>&);
  template <class U> friend size_t length(const list<U>&);

public:
  typedef T* iterator;
  typedef const T* const_iterator;
  typedef size_t size_type;
  typedef T value_type;
  typedef std::ptrdiff_t difference_type;
  typedef T& reference;
  typedef const T& const_reference;

  list() { resize(0); }
  list(const list& other) { clone(other.data, other.avail); }
  list(const_iterator b, const_iterator e) { clone(b, e); }
  list& operator=(const list&);
  ~list() { destroy(); }

  const_iterator begin() const { return data; }
  const_iterator end() const { return avail; }

private:
  iterator data;
  iterator avail;
  std::allocator<value_type> alloc;

  void clone(const_iterator, const_iterator);
  void destroy();
  void resize(size_type);
};

template <class T> bool is_null(const list<T>&);
template <class T> T list_ref(const list<T>&, size_t);
template <class T> list<T> append(const list<T>&, const list<T>&);
template <class T> list<T> reverse(const list<T>&);
template <class T> list<T> member(const T&, const list<T>&);

template <class T> std::ostream& operator<<(std::ostream&, const list<T>&);

// Class public functions
template <class T> list<T>& list<T>::operator=(const list<T>& other) {
  if (this != &other) {
    destroy();
    clone(other.data, other.avail);
  }

  return *this;
}

// Class private functions
template <class T> void list<T>::clone(const_iterator b, const_iterator e) {
  resize(e - b);
  uninitialized_copy(b, e, data);
}

template <class T> void list<T>::destroy() {
  if (data) {
    for (iterator it = data; it != avail; ++it) alloc.destroy(it);
    alloc.deallocate(data, avail - data);
  }
  
  data = avail = 0;
}

template <class T> void list<T>::resize(size_type n) {
  data = alloc.allocate(n);
  avail = data + n;
}

// Friend functions
template <class T> list<T> cons(const T& e, const list<T>& l) {
  list<T> new_l;
  new_l.resize(length(l) + 1);
  new_l.alloc.construct(new_l.data, e);
  uninitialized_copy(l.data, l.avail, new_l.data + 1);
  return new_l;
}

template <class T> T car(const list<T>& l) {
  if (is_null(l)) return T();
  return l.data[0];
}

template <class T> list<T> cdr(const list<T>& l) {
  if (is_null(l)) return list<T>();
  list<T> new_l;
  new_l.resize(length(l) - 1);
  uninitialized_copy(l.data + 1, l.avail, new_l.data);
  return new_l;
}

template <class T> size_t length(const list<T>& l) {
  return l.avail - l.data;
}

// Other functions
template <class T> bool is_null(const list<T>& l) {
  return (length(l) == 0);
}

template <class T> T list_ref(const list<T>& l, size_t i) {
  if (i == 0) return car(l);
  return list_ref(cdr(l), i - 1);
}

template <class T> list<T> app_helper(const list<T>& l1, const list<T>& l2) {
  if (is_null(l1)) return l2;
  list<T> new_l2 = cons(car(l1), l2);
  return app_helper(cdr(l1), new_l2);
}

template <class T> list<T> append(const list<T>& l1, const list<T>& l2) {
  return app_helper(reverse(l1), l2);
}

template <class T> list<T> rev_helper(const list<T>& l1, const list<T>& l2) {
  if (is_null(l1)) return l2;
  return rev_helper(cdr(l1), cons(car(l1), l2));
}

template <class T> list<T> reverse(const list<T>& l) {
  return rev_helper(l, list<T>());
}

template <class T> list<T> member(const T& e, const list<T>& l) {
  if (is_null(l)) return list<T>();
  if (e == car(l)) return l;
  return member(e, cdr(l));
}

template <class T> void print(std::ostream& out, const list<T>& l) {
  if (is_null(l)) return;
  out << car(l) << " ";
  print(out, cdr(l));
}

template <class T> std::ostream& operator<<(std::ostream& out, 
					    const list<T>& l) {
  out << "( ";
  print(out, l);
  out << ")";
  return out;
}
