-module(doctor).
-export([loop/0, monitor/0]).

loop() ->
    process_flag(trap_exit, true),
    receive
        new ->
            io:format("Creating and monitoring revolver.~n"),
            register(revolver, spawn_link(fun roulette:loop/0)),
            loop();
        {'EXIT', From, Reason} ->
            io:format("The shooter ~p died with reason ~p.", [From, Reason]),
            io:format(" Restarting. ~n"),
            self() ! new,
            loop()
    end.

monitor() ->
    process_flag(trap_exit, true),
    receive
        new ->
            io:format("Creating and monitoring doctor.~n"),
            register(doctor, spawn_link(fun loop/0)),
            monitor();
        {'EXIT', From, Reason} ->
            io:format("The doctor ~p died with reason ~p.", [From, Reason]),
            io:format(" Restarting.~n"),
            self() ! new,
            monitor()
    end.

%% 1> c(doctor).
%% {ok,doctor}
%% 2> M = spawn(fun doctor:monitor/0).
%% <0.39.0>
%% 3> M ! new.
%% Creating and monitoring doctor.
%% new
%% 4> is_process_alive(whereis(doctor)).
%% true
%% 5> exit(whereis(doctor), kill).
%% The doctor <0.41.0> died with reason killed. Restarting.
%% true
%% Creating and monitoring doctor.
%% 6> is_process_alive(whereis(doctor)).
%% true
