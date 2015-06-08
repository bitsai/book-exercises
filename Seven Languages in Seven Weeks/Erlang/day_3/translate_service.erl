-module(translate_service).
-export([loop/0, translate/2, monitor/0]).

loop() ->
    receive
        {From, "casa"} ->
            From ! "house",
            loop();
        {From, "blanca"} ->
            From ! "white",
            loop();
        {From, "muerte"} ->
            From ! "death",
            exit({translater,die,at,erlang:time()});
        {From, _} ->
            From ! "I don't understand.",
            loop()
end.

translate(To, Word) ->
    To ! {self(), Word},
    receive
        Translation -> Translation
    end.

monitor() ->
    process_flag(trap_exit, true),
    receive
        new ->
            io:format("Creating and monitoring translater.~n"),
            register(translater, spawn_link(fun loop/0)),
            monitor();
        {'EXIT', From, Reason} ->
            io:format("The translater ~p died with reason ~p.", [From, Reason]),
            io:format(" Restarting.~n"),
            self() ! new,
            monitor()
    end.

%% 1> c(translate_service).
%% {ok,translate_service}
%% 2> M = spawn(fun translate_service:monitor/0).
%% <0.39.0>
%% 3> M ! new.
%% Creating and monitoring translater.
%% new
%% 4> translate_service:translate(translater, "casa").
%% "house"
%% 5> translate_service:translate(translater, "muerte").
%% The translater <0.41.0> died with reason {translater,die,at,{2,11,43}}. Restarting.
%% "death"
%% Creating and monitoring translater.
%% 6> translate_service:translate(translater, "blanca").
%% "white"
