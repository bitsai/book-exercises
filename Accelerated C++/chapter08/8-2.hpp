#pragma once

#include <algorithm>
#include <iterator>

template <class InputIterator1, class InputIterator2>
bool my_equal(InputIterator1 b, InputIterator1 e, InputIterator2 b2) {
  for ( ;b != e; ++b) {
    if (*b != *b2) return false;
    ++b2;
  }
  
  return true;
}

template <class ForwardIterator1, class ForwardIterator2>
ForwardIterator1 my_search(ForwardIterator1 b, ForwardIterator1 e, 
			   ForwardIterator2 b2, ForwardIterator2 e2) {
  for ( ;b != e; ++b) if (my_equal(b2, e2, b)) return b;
  return e;
}

template <class InputIterator, class Type>
InputIterator my_find(InputIterator b, InputIterator e, const Type& t) {
  for ( ;b != e; ++b) if (*b == t) return b;
  return e;
}

template <class InputIterator, class Predicate>
InputIterator my_find_if(InputIterator b, InputIterator e, Predicate p) {
  for ( ;b !=e; ++b) if (p(*b)) return b;
  return e;
}

template <class InputIterator, class OutputIterator>
OutputIterator my_copy(InputIterator b, InputIterator e, OutputIterator d) {
  for ( ;b != e; ++b) *d++ = *b;
  return d;
}

template <class InputIterator, class OutputIterator, class Type>
OutputIterator my_remove_copy(InputIterator b, InputIterator e,
			      OutputIterator d, const Type& t) {
  for ( ;b != e; ++b) if (*b != t) *d++ = *b;
  return d;
}

template <class InputIterator, class OutputIterator, class Predicate>
OutputIterator my_remove_copy_if(InputIterator b, InputIterator e,
				 OutputIterator d, Predicate p) {
  for ( ;b != e; ++b) if (!p(*b)) *d++ = *b;
  return d;
}

template <class ForwardIterator, class Type>
ForwardIterator my_remove(ForwardIterator b, ForwardIterator e, 
			  const Type& t) {
  ForwardIterator first_fail = b;
  for ( ;b != e; ++b) if (*b != t) *first_fail++ = *b;
  return first_fail;
}

template <class InputIterator, class OutputIterator, class Function>
OutputIterator my_transform(InputIterator b, InputIterator e,
			    OutputIterator d, Function f) {
  for ( ;b != e; ++b) *d++ = f(*b);
  return d;
}

template <class ForwardIterator, class Predicate>
ForwardIterator my_partition(ForwardIterator b, ForwardIterator e,
			     Predicate p) {
  ForwardIterator first_fail = b;
  for ( ;b != e; ++b) if (p(*b)) swap(*first_fail++, *b);
  return first_fail;
}

template <class InputIterator, class Type>
Type my_accumulate(InputIterator b, InputIterator e, Type t) {
  for ( ;b != e; ++b) t += *b;
  return t;
}
