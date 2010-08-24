#pragma once

#include <cstring>
#include <iterator>
#include <memory>

using std::allocator;
using std::istream;
using std::ostream;
using std::ostream_iterator;
using std::strcmp;
using std::strlen;
using std::uninitialized_copy;

class Str {
 public:
  typedef size_t size_type;
  typedef char* iterator;
  typedef const char* const_iterator;

  Str() { create(0, '\0'); }
  Str(size_type n, char c) { create(n, c); }
  
  Str(const Str& s) { create(s.begin(), s.end()); }
  Str& operator =(const Str&);
  ~Str() { uncreate(); }

  Str(const char* cp) { create(cp, cp + strlen(cp)); }
  template <class In> Str(In i, In j) { create(i, j); }

  char& operator [](size_type i) { return chars[i]; }
  const char& operator [](size_type i) const { return chars[i]; }
  size_type size() const { return length - 1; }

  iterator begin() { return chars; }
  const_iterator begin() const { return chars; }

  iterator end() { return chars + length - 1; }
  const_iterator end() const { return chars + length - 1; }

  const char* c_str() const { return chars; }
  const char* data() const { return chars; }
  size_type copy(iterator, size_type, size_type = 0) const;

  Str& operator +=(const Str&);
  operator bool() const { return (size() > 0); }

  Str substr(size_type, size_type) const;
  template <class In> void insert(iterator, In, In);

 private:
  // chars is null-terminated, has length - 1 non-null chars
  size_type length;
  iterator chars;

  allocator<char> alloc;

  void create(size_type, char);
  template <class In> void create(In, In);
  void uncreate();

  void grow(size_type);
};

// Public functions
template <class In> void Str::insert(iterator p, In i, In j) {
  size_type new_length = length + (j - i);
  iterator new_chars = alloc.allocate(new_length);
  uninitialized_copy(chars, p, new_chars);
  uninitialized_copy(i, j, new_chars + (p - chars));
  uninitialized_copy(p, chars + length - 1, new_chars + (p - chars) + (j - i));
  alloc.construct(new_chars + new_length - 1, '\0');

  uncreate();
  
  length = new_length;
  chars = new_chars;
}

// Private functions
template <class In> void Str::create(In i, In j) {
  length = j - i + 1;
  chars = alloc.allocate(length);
  uninitialized_copy(i, j, chars);
  alloc.construct(chars + length - 1, '\0');
}

// Other functions
ostream& operator <<(ostream&, const Str&);
ostream_iterator<char>& operator <<(ostream_iterator<char>&, const Str&);
istream& operator >>(istream&, Str&);
istream& getline(istream&, Str&);

Str operator +(const Str&, const Str&);
Str operator +(const char*, const Str&);
Str operator +(const Str&, const char*);

inline bool operator <(const Str& lhs, const Str& rhs) {
  return (strcmp(lhs.c_str(), rhs.c_str()) < 0);
}

inline bool operator >(const Str& lhs, const Str& rhs) {
  return (strcmp(lhs.c_str(), rhs.c_str()) > 0);
}

inline bool operator <=(const Str& lhs, const Str& rhs) {
  return (strcmp(lhs.c_str(), rhs.c_str()) <= 0);
}

inline bool operator >=(const Str& lhs, const Str& rhs) {
  return (strcmp(lhs.c_str(), rhs.c_str()) >= 0);
}

inline bool operator ==(const Str& lhs, const Str& rhs) {
  return (strcmp(lhs.c_str(), rhs.c_str()) == 0);
}

inline bool operator !=(const Str& lhs, const Str& rhs) {
  return (strcmp(lhs.c_str(), rhs.c_str()) != 0);
}
