#ifndef GUARD_Ptr_h
#define GUARD_Ptr_h

#include <cstddef>
#include <stdexcept>

template <class T> class Ptr {
public:
	// new member to copy the object conditionally when needed
	void make_unique() {
		if (*refptr != 1) {
			--*refptr;
			refptr = new size_t(1);
			p = p? clone(p): 0;
		}
	}

	// the rest of the class looks like `Ref_handle' except for its name
	Ptr(): p(0), refptr(new size_t(1)) { }
	Ptr(T* t): p(t), refptr(new size_t(1)) { }
	Ptr(const Ptr& h): p(h.p), refptr(h.refptr) { ++*refptr; }

	Ptr& operator=(const Ptr&);    // implemented analogously to 14.2/261
	~Ptr();                        // implemented analogously to 14.2/262
	operator bool() const { return p; }
	T& operator*() const;          // implemented analogously to 14.2/261
	T* operator->() const;         // implemented analogously to 14.2/261

private:
	T* p;
#ifdef _MSC_VER
	size_t* refptr;
#else
	std::size_t* refptr;
#endif
};

template <class T> T* clone(const T* tp)
{
	return tp->clone();
}



template<class T>
T& Ptr<T>::operator*() const { if (p) return *p; throw std::runtime_error("unbound Ptr"); }

template<class T>
T* Ptr<T>::operator->() const { if (p) return p; throw std::runtime_error("unbound Ptr"); }


template<class T>
Ptr<T>& Ptr<T>::operator=(const Ptr& rhs)
{
        ++*rhs.refptr;
        // \f2free the lhs, destroying pointers if appropriate\fP
        if (--*refptr == 0) {
                delete refptr;
                delete p;
        }

        // \f2copy in values from the right-hand side\fP
        refptr = rhs.refptr;
        p = rhs.p;
        return *this;
}

template<class T> Ptr<T>::~Ptr()
{
        if (--*refptr == 0) {
                delete refptr;
                delete p;
        }
}


#endif
