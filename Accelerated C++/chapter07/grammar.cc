#ifdef _MSC_VER
#pragma warning(disable:4503)      // `silence compiler complaints about generated names being too long'
#endif

#include <algorithm>
#include <cstdlib>
#include <iostream>
#include <map>
#include <stdexcept>
#include <string>
#include <time.h>
#include <vector>

#include "split.h"

using std::istream;           using std::cin;
using std::copy;              using std::cout;
using std::endl;              using std::find;
using std::getline;           using std::logic_error;
using std::map;               using std::string;
using std::vector;            using std::domain_error;
using std::rand;

typedef vector<string> Rule;
typedef vector<Rule> Rule_collection;
typedef map<string, Rule_collection> Grammar;

// read a grammar from a given input stream
Grammar read_grammar(istream& in) {
  Grammar ret;
  string line;

  // read the input
  while (getline(in, line)) {
    // `split' the input into words
    vector<string> entry = split(line);

    if (!entry.empty())
      // use the category to store the associated rule
      ret[entry[0]].push_back(Rule(entry.begin() + 1, entry.end()));
  }

  return ret;
}

void gen_aux(const Grammar&, const string&, vector<string>&);

// return a random integer in the range `[0,' `n)'
int nrand(int n) {
  if (n <= 0 || n > RAND_MAX)
    throw domain_error("Argument to nrand is out of range");

  const int bucket_size = RAND_MAX / n;
  int r;

  do r = rand() / bucket_size; while (r >= n);

  return r;
}

vector<string> gen_sentence(const Grammar& g) {
  vector<string> ret;
  gen_aux(g, "<sentence>", ret);
  return ret;
}

bool bracketed(const string& s) {
  return s.size() > 1 && s[0] == '<' && s[s.size() - 1] == '>';
}

void gen_aux(const Grammar& g, const string& word, vector<string>& ret) {
  if (!bracketed(word)) {
    ret.push_back(word);
  } else {
    // locate the rule that corresponds to `word'
    Grammar::const_iterator it = g.find(word);

    if (it == g.end())
      throw logic_error("empty rule");

    // fetch the set of possible rules
    const Rule_collection& c = it->second;

    // from which we select one at random
    const Rule& r = c[nrand(c.size())];

    // recursively expand the selected rule
    for (Rule::const_iterator i = r.begin(); i != r.end(); ++i)
      gen_aux(g, *i, ret);
  }
}

int main() {
  // generate the sentence
  vector<string> sentence = gen_sentence(read_grammar(cin));

  // write the first word, if any
#ifdef _MSC_VER
  std::vector<string>::const_iterator it = sentence.begin();
#else
  vector<string>::const_iterator it = sentence.begin();
#endif
  if (!sentence.empty()) {
    cout << *it;
    ++it;
  }

  // write the rest of the words, each preceded by a space
  while (it != sentence.end()) {
    cout << " " << *it;
    ++it;
  }

  cout << endl;

  return 0;
}
