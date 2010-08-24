#include <iostream>
#include <string>

// say what standard-library names we use
using std::cin;         using std::endl;
using std::cout;        using std::string;

int main() {
  // ask for the person's name
  cout << "Please enter your first name: ";

  // read the name
  string name;
  cin >> name;

  // build the message that we intend to write
  const string greeting = "Hello, " + name + "!";

  // the number of blanks surrounding the greeting
  const int pad = 0;

  // the number of rows and columns to write
  const int rows = pad * 2 + 3;
  const string::size_type cols = greeting.size() + pad * 2 + 2;

  // write a blank line to separate the output from the input
  cout << endl;

  // write `rows' rows of output
  // invariant: we have written `r' rows so far
  for (int r = 0; r != rows; ++r) {
    string::size_type c = 0;

    // invariant: we have written `c' characters so far in the current row
    while (c != cols) {
      // is it time to write the greeting?
      if (r == pad + 1 && c == pad + 1) {
	cout << greeting;
	c += greeting.size();
      } else {
	// are we on the border?
	if (r == 0 || r == rows - 1 ||
	    c == 0 || c == cols - 1)
	  cout << "*";
	else
	  cout << " ";
	++c;
      }
    }

    cout << endl;
  }

  return 0;
}

