#include "Pic.h"

using namespace std;

Picture frame(const Picture& pic) {
  return new Frame_Pic(pic.p);
}

Picture hcat(const Picture& l, const Picture& r) {
  return new HCat_Pic(l.p, r.p);
}

Picture vcat(const Picture& t, const Picture& b) {
  return new VCat_Pic(t.p, b.p);
}

Picture::Picture(const Vec<Str>& v): p(new String_Pic(v)) { }

ostream& operator<<(ostream& os, const Picture& picture) {
  const Pic_base::ht_sz ht = picture.p->height();

  for (Pic_base::ht_sz i = 0; i != ht; ++i) {
    picture.p->display(os, i, false);
    os << endl;
  }

  return os;
}

Pic_base::wd_sz String_Pic::width() const {
  Pic_base::wd_sz n = 0;

  for (Pic_base::ht_sz i = 0; i != data.size(); ++i)
    n = max(n, data[i].size());

  return n;
}

void String_Pic::display(ostream& os, ht_sz row, bool do_pad) const {
  wd_sz start = 0;

  // write the row if we're still in range
  if (row < height()) {
    os << data[row];
    start = data[row].size();
  }

  // pad the output if necessary
  if (do_pad) pad(os, start, width());
}

void VCat_Pic::display(ostream& os, ht_sz row, bool do_pad) const {
  wd_sz w = 0;

  if (row < top->height()) {
    // we are in the top subpicture
    top->display(os, row, do_pad);
    w = top->width();
  } else if (row < height()) {
    // we are in the bottom subpicture
    bottom->display(os, row - top->height(), do_pad);
    w = bottom->width();
  }

  if (do_pad) pad(os, w, width());
}

void HCat_Pic::display(ostream& os, ht_sz row, bool do_pad) const {
  ht_sz left_row = row, right_row = row;

  if (left->height() > right->height()) {
    ht_sz v_pad = (left->height() - right->height()) / 2;
    right_row = (row < v_pad) ? right->height() : row - v_pad;
  } else {
    ht_sz v_pad = (right->height() - left->height()) / 2;
    left_row = (row < v_pad) ? left->height() : row - v_pad;
  }

  left->display(os, left_row, do_pad || right_row < right->height());
  right->display(os, right_row, do_pad);
}

void Frame_Pic::display(ostream& os, ht_sz row, bool do_pad) const {
  if (row >= height()) {
    // out of range
    if (do_pad) pad(os, 0, width());
  } else {
    if (row == 0 || row == height() - 1) {
      // top or bottom row
      os << corner;
      os << Str(width() - 2, top_bottom);
      os << corner;
    } else if (row == 1 || row == height() - 2) {
      // second from top or bottom row
      os << side;
      pad(os, 1, width() - 1);
      os << side;
    } else {
      // interior row
      os << side << " ";
      p->display(os, row - 2, true);
      os << " " << side;
    }
  }
}

void Pic_base::pad(ostream& os, wd_sz beg, wd_sz end) {
  while (beg != end) {
    os << " ";
    ++beg;
  }
}

void VCat_Pic::reframe(char c, char t_b, char s) {
  top->reframe(c, t_b, s);
  bottom->reframe(c, t_b, s);
}

void HCat_Pic::reframe(char c, char t_b, char s) {
  left->reframe(c, t_b, s);
  right->reframe(c, t_b, s);
}

void Frame_Pic::reframe(char c, char t_b, char s) {
  p->reframe(c, t_b, s);

  corner = c;
  top_bottom = t_b;
  side = s;
}
