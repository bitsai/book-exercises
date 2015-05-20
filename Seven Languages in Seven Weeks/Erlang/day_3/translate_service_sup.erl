-module(translate_service_sup).
-behaviour(supervisor).
-export([start_link/0, init/1]).

start_link() ->
    supervisor:start_link(translate_service_sup, []).

init(_Args) ->
    {ok, {{one_for_one, 10, 1},
          [{translate_service,
            {translate_service, start_link, []},
            permanent, 
            brutal_kill,
            worker, 
            [translate_service]}]}}.
