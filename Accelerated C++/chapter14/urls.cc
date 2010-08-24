#include <algorithm>
#include <cctype>
#include "Str.h"
#include "Vec.h"

#include "urls.h"

using namespace std;

bool not_url_char(char);

Str::const_iterator url_end(Str::const_iterator, Str::const_iterator);
Str::const_iterator url_beg(Str::const_iterator, Str::const_iterator);

Vec<Str> find_urls(const Str& s) {
  Vec<Str> ret;
  typedef Str::const_iterator iter;
  iter b = s.begin(), e = s.end();

  while (b != e) {
    b = url_beg(b, e);

    if (b != e) {
      iter after = url_end(b, e);
      ret.push_back(Str(b, after));
      b = after;
    }
  }

  return ret;
}

Str::const_iterator url_end(Str::const_iterator b, Str::const_iterator e) {
  return find_if(b, e, not_url_char);
}

bool not_url_char(char c) {
  static const Str url_ch = "~;/?:@=&$-_.+!*'(),";

  return !(isalnum(c) ||
	   find(url_ch.begin(), url_ch.end(), c) != url_ch.end());
}

Str::const_iterator url_beg(Str::const_iterator b, Str::const_iterator e) {
  static const Str sep = "://";
  typedef Str::const_iterator iter;
  iter i = b;

  while ((i = search(i, e, sep.begin(), sep.end())) != e) {
    if (i != b && i + sep.size() != e) {
      iter beg = i;
      while (beg != b && isalpha(beg[-1])) --beg;
      if (beg != i && !not_url_char(i[sep.size()])) return beg;
    }

    i += sep.size();
  }

  return e;
}
