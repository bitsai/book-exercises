%{
#include <iostream>
#include <string>
  
#define AM_VAL 0
#define PM_VAL 1
  
  using namespace std;
  
  int yylex(void);
  void yyerror(string);
  %}

%token DIGIT AM PM SEP

%%
input: /* empty */ | input line ;
line: '\n' | list '\n' ;
list: time | time SEP list ;

time: hour suffix { cout << "Hour: " << $1 << ", Suffix: " << $2 << endl; }
| hour ':' minute { cout << "Hour: " << $1 << ", Minute: " << $3 << endl; }
| hour ':' minute suffix { 
  cout << "Hour: " << $1 << ", Minute: " << $3 << ", Suffix: " << $4 << endl; 
  }
;

hour: DIGIT | two_digits ;
minute: two_digits ;
two_digits: DIGIT DIGIT { $$ = $1 * 10 + $2; } ;
suffix: AM | PM ;
%%

int yylex() {
  char c = getchar();
  
  if (isdigit(c)) {
    yylval = atoi(&c);
    return DIGIT;
  }

  if (c == 'a' && cin.peek() == 'm') {
    getchar();
    yylval = AM_VAL;
    return AM;
  }

  if (c == 'p' && cin.peek() == 'm') {
    getchar();
    yylval = PM_VAL;
    return PM;
  }

  if (c == ',') {
    if (cin.peek() == ' ') getchar();
    return SEP;
  }

  if (c == EOF)
    return 0;

  return c;
}

void yyerror(string msg) {
  cout << msg << endl;
}

int main(void) {
  return yyparse();
}
