-module(day_2).
-export([find/2, total_prices/1, tic_tac_toe/1]).

find([], _) -> not_found;
find([{Keyword, Value}|_], Keyword) -> Value;
find([_|Tail], Keyword) -> find(Tail, Keyword).

total_prices(ShoppingList) -> [{Item, Quantity * Price} || {Item, Quantity, Price} <- ShoppingList].

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
