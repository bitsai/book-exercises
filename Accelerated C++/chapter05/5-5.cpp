#include <iostream>
#include <string>
#include <vector>

using namespace std;

vector<string> center(const vector<string>& picture) {
  vector<string> new_picture;
  vector<string>::size_type max_length = 0;

  for (vector<string>::const_iterator i = picture.begin(); 
       i != picture.end(); ++i)
    if (i->size() > max_length)
      max_length = i->size();

  for (vector<string>::const_iterator i = picture.begin(); 
       i != picture.end(); ++i) {
    vector<string>::size_type difference = max_length - i->size();
    if (difference % 2 == 0) {
    	vector<string>::size_type padding = (difference) / 2;
    }
    else {
	vector<string>::size_type padding = (difference-1) / 2;
    	
    }
	string new_line(padding, ' ');
    	new_line += *i;
    new_picture.push_back(new_line);
  }

  return new_picture;
}

int main() {
  vector<string> picture;
	
  picture.push_back("*");
  picture.push_back("***");
  picture.push_back("*****");

  vector<string> new_picture = center(picture);

  for (vector<string>::const_iterator i = new_picture.begin(); 
       i != new_picture.end(); ++i)
    cout << *i << endl;

  return 0;
}
