revAcc([], Acc, Acc).
revAcc([X|Tail], Acc, Reversed) :-
    revAcc(Tail, [X|Acc], Reversed).

rev(A, B) :-
    revAcc(A, [], B).

%% | ?- rev([1,2,3,4,5], What).

%% What = [5,4,3,2,1]

%% yes

min([X], X).
min([X|Tail], Min) :-
    min(Tail, TailMin),
    (   X =< TailMin
     -> Min = X
     ;  Min = TailMin
    ).

%% | ?- min([5,3,1,2,4], What).

%% What = 1 ? a

%% no

srt([], []).
srt([X], [X]).
srt([X|Tail], Sorted) :-
    srt(Tail, [Y|BiggerThanY]),
    (   X =< Y
     -> Sorted = [X, Y|BiggerThanY]
     ;  srt([X|BiggerThanY], XAndBiggerThanY),
        Sorted = [Y|XAndBiggerThanY]
    ).

%% | ?- srt([5,3,1,2,4], What).

%% What = [1,2,3,4,5] ? a

%% no
