#include <algorithm>
#include <cctype>
#include <iostream>
#include "Str.h"
#include "Vec.h"

#include "urls.h"

using std::cout;
using std::cin;
using std::endl;
using std::find_if;





int main() {
        Str s;
        while (getline(cin, s)) {
                Vec<Str> v = find_urls(s);
#ifdef _MSC_VER
                for (Vec<Str>::const_iterator i = v.begin();
#else
                for (Vec<Str>::const_iterator i = v.begin();
#endif
                        i != v.end(); ++i)
                        cout << *i << endl;
        }
        return 0;
}
