-module(day_2).
-export([find/2, total_prices/1, tic_tac_toe/1]).

find([], _) -> not_found;
find([{Keyword, Value}|_], Keyword) -> Value;
find([_|Tail], Keyword) -> find(Tail, Keyword).

%% 4> day_2:find([{erlang, "a functional language"}, {ruby, "an OO language"}], erlang).
%% "a functional language"
%% 5> day_2:find([{erlang, "a functional language"}, {ruby, "an OO language"}], prolog).
%% not_found

total_prices(ShoppingList) -> [{Item, Quantity * Price} || {Item, Quantity, Price} <- ShoppingList].

%% 6> day_2:total_prices([{hot_dog, 3, 1.50}, {smoothie, 1, 1.65}, {pizza, 1, 2.0}]).
%% [{hot_dog,4.5},{smoothie,1.65},{pizza,2.0}]

%% 3 possible horizontal victories
tic_tac_toe([Z,Z,Z,_,_,_,_,_,_]) when Z == x orelse Z == o -> Z;
tic_tac_toe([_,_,_,Z,Z,Z,_,_,_]) when Z == x orelse Z == o -> Z;
tic_tac_toe([_,_,_,_,_,_,Z,Z,Z]) when Z == x orelse Z == o -> Z;
%% 3 possible vertical victories
tic_tac_toe([Z,_,_,Z,_,_,Z,_,_]) when Z == x orelse Z == o -> Z;
tic_tac_toe([_,Z,_,_,Z,_,_,Z,_]) when Z == x orelse Z == o -> Z;
tic_tac_toe([_,_,Z,_,_,Z,_,_,Z]) when Z == x orelse Z == o -> Z;
%% 2 possible diagonal victories
tic_tac_toe([Z,_,_,_,Z,_,_,_,Z]) when Z == x orelse Z == o -> Z;
tic_tac_toe([_,_,Z,_,Z,_,Z,_,_]) when Z == x orelse Z == o -> Z;
%% otherwise...
tic_tac_toe(Moves) ->
    case lists:all(fun(M) -> M == x orelse M == o end, Moves) of
        %% no more possible moves
        true -> cat;
        %% no winner yet
        false -> no_winner
    end.

%% 8> day_2:tic_tac_toe([nil,nil,nil,nil,nil,nil,nil,nil,nil]).
%% no_winner
%% 9> day_2:tic_tac_toe([o,x,x,o,x,nil,o,nil,nil]).
%% o
%% 10> day_2:tic_tac_toe([o,x,o,o,x,x,x,o,x]).
%% cat
