#include <cstdlib>
#include <iostream>
#include <iterator>
#include <list>
#include <time.h>

#include "8-5-grammar-utils.hpp"

using namespace std;

int main() {
  srand(time(NULL));

  // generate the sentence
  ostream_iterator<string> out_it(cout, " ");
  gen_sentence(read_grammar(cin), out_it);
  cout << endl;

  return 0;
}
