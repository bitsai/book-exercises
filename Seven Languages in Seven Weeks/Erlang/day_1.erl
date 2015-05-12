-module(day_1).
-export([count_words/1, count_to_ten/1]).

count_words([]) -> 0;
count_words([X]) when X /= 32 -> 1;
count_words([X,32|Tail]) when X /= 32 -> 1 + count_words(Tail);
count_words([_|Tail]) -> 0 + count_words(Tail).

count_to_ten(10) -> io:format("10~n");
count_to_ten(X) when X < 10 -> io:format("~w~n", [X]),
                               count_to_ten(X + 1);
count_to_ten(X) when X > 10 -> io:format("~w~n", [X]),
                               count_to_ten(X - 1).
