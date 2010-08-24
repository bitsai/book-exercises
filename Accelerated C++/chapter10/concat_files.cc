#include <iostream>
#include <fstream>
#include <string>

using std::cerr;
using std::cout;
using std::endl;
using std::getline;
using std::ifstream;
using std::string;

int main(int argc, char **argv)
{
	int fail_count = 0;
	// for each file in the input list
	for (int i = 1; i < argc; ++i) {
		ifstream in(argv[i]);

		// if it exists, write its contents, otherwise generate an error message
		if (in) {
			string s;
			while (getline(in, s))
				cout << s << endl;
		} else {
			cerr << "cannot open file " << argv[i] << endl;
			++fail_count;
		}
	}
	return fail_count;
}

