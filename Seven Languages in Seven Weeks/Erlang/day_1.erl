-module(day_1).
-export([count_words/1, count_to_ten/1, print/1]).

count_words([]) -> 0;
count_words([X]) when X /= 32 -> 1;
count_words([X,32|Tail]) when X /= 32 -> 1 + count_words(Tail);
count_words([_|Tail]) -> 0 + count_words(Tail).

%% 4> day_1:count_words("hello world").
%% 2

count_to_ten(10) -> io:format("10~n");
count_to_ten(X) when X < 10 -> io:format("~w~n", [X]),
                               count_to_ten(X + 1);
count_to_ten(X) when X > 10 -> io:format("~w~n", [X]),
                               count_to_ten(X - 1).

%% 5> day_1:count_to_ten(0).
%% 0
%% 1
%% 2
%% 3
%% 4
%% 5
%% 6
%% 7
%% 8
%% 9
%% 10
%% ok
%% 6> day_1:count_to_ten(20).
%% 20
%% 19
%% 18
%% 17
%% 16
%% 15
%% 14
%% 13
%% 12
%% 11
%% 10
%% ok

print(success) -> io:format("success~n");
print({error, Message}) -> io:format("error: ~s~n", [Message]).

%% 7> day_1:print(success).
%% success
%% ok
%% 8> day_1:print({error, "Oh noes!"}).
%% error: Oh noes!
%% ok
