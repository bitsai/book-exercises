#include <cmath>
#include <cstdlib>
#include <iostream>
#include <stdexcept>

#include <limits.h>

using namespace std;

#define MY_RAND_MAX 32767

int nrand(int n) {
  if (n <= 0)
    throw domain_error("Argument to nrand is out of range");
  
  int r;

  if (n <= MY_RAND_MAX) {						       
    const int bucket_size = MY_RAND_MAX / n;

    do {
      int bucket = rand() / bucket_size;
      r = bucket;
    } while (r >= n);
  } else {
    const int bucket_size = ceil(n / MY_RAND_MAX);

    do {
      const int bucket = nrand(MY_RAND_MAX);
      const int remainder = nrand(bucket_size);
      r = (bucket - 1) * bucket_size + remainder;
    } while (r >= n);
  }

  return r;
}

int main() {
  int limit;

  while (cin >> limit)
    cout << nrand(limit) << endl;

  return 0;
}
