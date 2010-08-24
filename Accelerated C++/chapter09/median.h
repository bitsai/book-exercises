#ifndef GUARD_median_h
#define GUARD_median_h

#include <algorithm>
#include <stdexcept>
#include <vector>

using std::domain_error;
using std::sort;
using std::vector;
template <class T>
T median(vector<T> v)
{
#ifdef _MSC_VER
        typedef typename std::vector<T>::size_type vec_sz;
#else
	typedef typename vector<T>::size_type vec_sz;
#endif

	vec_sz size = v.size();
	if (size == 0)
		throw domain_error("median of an empty vector");

	sort(v.begin(), v.end());

	vec_sz mid = size/2;

	return size % 2 == 0 ? (v[mid] + v[mid-1]) / 2 : v[mid];
}
#endif

