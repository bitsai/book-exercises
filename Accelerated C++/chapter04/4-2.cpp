#include <cmath>
#include <iomanip>
#include <iostream>

using namespace std;

int main() {  
  for (int i = 1; i <= 100; ++i) {
    cout << setw(3)
	 << i
	 << setw(6)
	 << i * i
	 << endl;
  }
  
  return 0;
}
