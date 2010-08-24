#include <iostream>

using std::cout;
using std::endl;

int main(int argc, char** argv)
{
	// if there are command-line arguments, write them
	if (argc > 1) {
		cout << argv[1];               // write the first argument

		// write each remaining argument with a space before it
		for (int i = 2; i != argc; ++i)
			cout << " " << argv[i];    // `argv[i]' is a `char*'
	}
	cout << endl;
	return 0;
}

