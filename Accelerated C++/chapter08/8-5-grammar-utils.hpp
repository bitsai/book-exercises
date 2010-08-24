#pragma once

#include <map>
#include <stdexcept>
#include <string>
#include <vector>

typedef std::vector<std::string> Rule;
typedef std::vector<Rule> Rule_collection;
typedef std::map<std::string, Rule_collection> Grammar;

int nrand(int);

bool bracketed(const std::string&);

Grammar read_grammar(std::istream&);

template <class OutputIterator>
OutputIterator gen_aux(const Grammar& g, const std::string& word, 
		       OutputIterator d) {
  if (!bracketed(word)) {
    *d = word;
  } else {
    // locate the rule that corresponds to `word'
    Grammar::const_iterator it = g.find(word);

    if (it == g.end())
      throw std::logic_error("empty rule");

    // fetch the set of possible rules
    const Rule_collection& c = it->second;

    // from which we select one at random
    const Rule& r = c[nrand(c.size())];

    // recursively expand the selected rule
    for (Rule::const_iterator i = r.begin(); i != r.end(); ++i)
      gen_aux(g, *i, d);
  }
}

template <class OutputIterator>
OutputIterator gen_sentence(const Grammar& g, OutputIterator d) {
  gen_aux(g, "<sentence>", d);
}
