-module(doctor).
-export([loop/0, monitor/0, monitor2/0, start/0]).

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
            case whereis(doctor) of
                undefined ->
                    io:format("Creating and monitoring doctor.~n"),
                    register(doctor, spawn_link(fun loop/0));
                _ -> do_nothing
            end,
            case whereis(monitor2) of
                undefined ->
                    io:format("Creating and monitoring monitor2.~n"),
                    register(monitor2, spawn_link(fun monitor2/0));
                _ -> do_nothing
            end,
            monitor();
        {'EXIT', From, Reason} ->
            io:format("The process ~p died with reason ~p.", [From, Reason]),
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

monitor2() ->
    process_flag(trap_exit, true),
    receive
        new ->
            io:format("Creating and monitoring monitor.~n"),
            register(monitor, spawn_link(fun monitor/0)),
            monitor2();
        {'EXIT', From, Reason} ->
            io:format("The monitor ~p died with reason ~p.", [From, Reason]),
            io:format(" Restarting.~n"),
            self() ! new,
            monitor2()
    end.

start() ->
    register(monitor2, spawn(fun monitor2/0)).

%% 1> c(doctor).
%% {ok,doctor}
%% 2> doctor:start().
%% true
%% 3> monitor2 ! new.
%% Creating and monitoring monitor.
%% new
%% 4> is_process_alive(whereis(monitor)).
%% true
%% 5> exit(whereis(monitor), kill).
%% The monitor <0.41.0> died with reason killed. Restarting.
%% true
%% Creating and monitoring monitor.
%% 6> is_process_alive(whereis(monitor)).
%% true
%% 7> is_process_alive(whereis(monitor2)).
%% true
%% 8> exit(whereis(monitor2), kill).
%% The process <0.39.0> died with reason killed. Restarting.
%% true
%% Creating and monitoring doctor.
%% Creating and monitoring monitor2.
%% 9> is_process_alive(whereis(monitor2)).
%% true
