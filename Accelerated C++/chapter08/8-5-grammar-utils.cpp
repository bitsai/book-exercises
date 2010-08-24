#include <iostream>

#include "8-5-grammar-utils.hpp"
#include "split.h"

using namespace std;

// return a random integer in the range `[0,' `n)'
int nrand(int n) {
  if (n <= 0 || n > RAND_MAX)
    throw domain_error("Argument to nrand is out of range");

  const int bucket_size = RAND_MAX / n;
  int r;

  do r = rand() / bucket_size; while (r >= n);

  return r;
}

bool bracketed(const string& s) {
  return s.size() > 1 && s[0] == '<' && s[s.size() - 1] == '>';
}

// read a grammar from a given input stream
Grammar read_grammar(istream& in) {
  Grammar ret;
  string line;

  // read the input
  while (getline(in, line)) {
    // `split' the input into words
    vector<string> entry;
    split(line, back_inserter(entry));

    if (!entry.empty())
      // use the category to store the associated rule
      ret[entry[0]].push_back(Rule(entry.begin() + 1, entry.end()));
  }

  return ret;
}
