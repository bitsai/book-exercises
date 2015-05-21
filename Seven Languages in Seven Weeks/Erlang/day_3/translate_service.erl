-module(translate_service).
-export([loop/0, translate/2, start_link/0]).

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
            exit({translate_service,die,at,erlang:time()});
        {From, _} ->
            From ! "I don't understand.",
            loop()
end.

translate(To, Word) ->
    To ! {self(), Word},
    receive
        Translation -> Translation
    end.

start_link() ->
    Pid = spawn_link(fun loop/0),
    register(translater, Pid),
    {ok, Pid}.
