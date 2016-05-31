rev_acc([], Acc, Acc).
rev_acc([Head|Tail], Acc, Reversed) :-
    %% insert Head as head of Acc and recurse
    rev_acc(Tail, [Head|Acc], Reversed).

rev(List, Reversed) :-
    rev_acc(List, [], Reversed).

%% | ?- rev([1,2,3,4,5], What).

%% What = [5,4,3,2,1]

%% yes

min([X], X).
min([Head1, Head2|Tail], Min) :-
    %% if Head1 <= Head2, discard Head2 and recurse
    Head1 =< Head2,
    min([Head1|Tail], Min).
min([Head1, Head2|Tail], Min) :-
    %% if Head1 > Head2, discard Head1 and recurse
    Head1 > Head2,
    min([Head2|Tail], Min).

%% | ?- min([5,3,1,2,4], What).

%% What = 1 ? a

%% no

insert(X, [], [X]).
insert(X, [Head|Tail], [X, Head|Tail]) :-
    %% if X <= Head, insert X as head
    X =< Head.
insert(X, [Head|Tail], [Head|Tail2]) :-
    %% if X > Head, insert X to proper position in Tail
    X > Head,
    insert(X, Tail, Tail2).

isort_acc([], Acc, Acc).
isort_acc([Head|Tail], Acc, Sorted) :-
    %% insert Head to proper position in Acc and recurse
    insert(Head, Acc, Acc2),
    isort_acc(Tail, Acc2, Sorted).

isort(List, Sorted) :- isort_acc(List, [], Sorted).

%% | ?- isort([5,3,1,2,4], What).

%% What = [1,2,3,4,5] ? a

%% no
