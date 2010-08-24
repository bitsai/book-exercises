#include <iterator>
#include <iostream>
#include <string>
#include "split.h"

using std::cin;
using std::cout;
using std::getline;
using std::ostream_iterator;
using std::string;

int main()
{
	string s;
	while (getline(cin, s))
		split(s, ostream_iterator<string>(cout, "\n"));
	return 0;
}

