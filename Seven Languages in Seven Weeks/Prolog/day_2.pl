rev([], []).
rev([Head|Tail], Reversed) :-
    rev(Tail, Xs),
    append(Xs, [Head], Reversed).

%% | ?- rev([1,2,3,4,5], What).

%% What = [5,4,3,2,1]

%% yes

min([X], X).
min([X|[Y|Tail]], Z) :-
    X < Y,
    min([X|Tail], Z).
min([X|[Y|Tail]], Z) :-
    X >= Y,
    min([Y|Tail], Z).

%% | ?- min([5,3,1,2,4], What).           

%% What = 1 ? a

%% no

sort1([], []).
sort1([X], [X]).
sort1([X|Tail], [X|Sorted]) :-
    sort1(Tail, [Y|Tail2]),
    X < Y,
    sort1([Y|Tail2], Sorted).
sort1([X|Tail], [Y|Sorted]) :-
    sort1(Tail, [Y|Tail2]),
    X >= Y,
    sort1([X|Tail2], Sorted).

%% | ?- sort1([5,3,1,2,4], What).

%% What = [1,2,3,4,5] ? a

%% no
