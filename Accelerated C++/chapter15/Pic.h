#pragma once

#include <algorithm>
#include <iostream>

#include "Ptr.h"
#include "Str.hpp"
#include "Vec.h"

// forward declaration, described in 15.3/288
class Picture;

class Pic_base {
  friend std::ostream& operator<<(std::ostream&, const Picture&);
  friend class Frame_Pic;
  friend class HCat_Pic;
  friend class VCat_Pic;
  friend class String_Pic;
  friend class Picture;

  // no `public' interface (except for the destructor)
  typedef Vec<Str>::size_type ht_sz;
  typedef Str::size_type wd_sz;

  // this class is an abstract base class
  virtual wd_sz width() const = 0;
  virtual ht_sz height() const = 0;
  virtual void display(std::ostream&, ht_sz, bool) const = 0;
  virtual void reframe(char, char, char) = 0;

 public:
  virtual ~Pic_base() { }

 protected:
  static void pad(std::ostream& os, wd_sz, wd_sz);
};

class Picture {
  friend std::ostream& operator<<(std::ostream&, const Picture&);
  friend Picture frame(const Picture&);
  friend Picture hcat(const Picture&, const Picture&);
  friend Picture vcat(const Picture&, const Picture&);

 public:
  Picture(const Vec<Str>& = Vec<Str>());
  void reframe(char c, char t_b, char s) { p->reframe(c, t_b, s); }

 private:
 Picture(Pic_base* ptr): p(ptr) { }
  Ptr<Pic_base> p;
};

// operations on `Picture's
Picture frame(const Picture&);
Picture hcat(const Picture&, const Picture&);
Picture vcat(const Picture&, const Picture&);
std::ostream& operator<<(std::ostream&, const Picture&);

class String_Pic: public Pic_base {
  friend class Picture;
  Vec<Str> data;
 String_Pic(const Vec<Str>& v): data(v) { }

  ht_sz height() const { return data.size(); }
  wd_sz width() const;
  void display(std::ostream&, ht_sz, bool) const;
  void reframe(char, char, char) { }
};

class VCat_Pic: public Pic_base {
  friend Picture vcat(const Picture&, const Picture&);

  Ptr<Pic_base> top, bottom;

 VCat_Pic(const Ptr<Pic_base>& t, const Ptr<Pic_base>& b):
  top(t), bottom(b) { }

  wd_sz width() const { return std::max(top->width(), bottom->width()); }
  ht_sz height() const { return top->height() + bottom->height(); }
  void display(std::ostream&, ht_sz, bool) const;
  void reframe(char, char, char);
};

class HCat_Pic: public Pic_base {
  friend Picture hcat(const Picture&, const Picture&);

  Ptr<Pic_base> left, right;

 HCat_Pic(const Ptr<Pic_base>& l, const Ptr<Pic_base>& r):
  left(l), right(r) { }

  wd_sz width() const { return left->width() + right->width(); }
  ht_sz height() const { return std::max(left->height(), right->height()); }
  void display(std::ostream&, ht_sz, bool) const;
  void reframe(char, char, char);
};

class Frame_Pic: public Pic_base {
  friend Picture frame(const Picture&);

  Ptr<Pic_base> p;
  char corner;
  char top_bottom;
  char side;

 Frame_Pic(const Ptr<Pic_base>& pic): p(pic), corner('*'), top_bottom('-'), 
    side('|') { }

  wd_sz width() const { return p->width() + 4; }
  ht_sz height() const { return p->height() + 4; }
  void display(std::ostream&, ht_sz, bool) const;
  void reframe(char, char, char);
};
