#include <iostream>
#include <string>

#include "8-2.hpp"

using namespace std;

bool passes(char c) {
  return c == 'P';
}

int main() {
  string grades = "PPFFPFP";
  my_partition(grades.begin(), grades.end(), passes);
  cout << grades << endl;
  
  return 0;
}
