#include <iostream>
#include <string>
#include <vector>

#include "Pic.h"

using std::cout;
using std::endl;
using std::string;
using std::vector;

char *init[] = { "Paris", "in the", "Spring" };

int main()
{
        vector<string> i;
        for (int j = 0; j < 3; ++j)
                i.push_back(init[j]);

	Picture p(i);
	cout << p << endl;
	Picture q = p;
	cout << q << endl;
	return 0;
}
