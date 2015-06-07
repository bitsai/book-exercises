-module(logger).
-behaviour(gen_server).

-export([init/1, start_link/0, handle_call/3]).

init(_Args) ->
    {ok, logging}.

start_link() ->
    gen_server:start_link({local, logger}, logger, [], []).

handle_call(Msg, _From, State) ->
    Formatted = io_lib:format("~s\n", [Msg]),
    file:write_file("/tmp/logger.txt", Formatted, [append]),
    {reply, Msg, State}.

%% 1> c(logger).
%% logger.erl:2: Warning: undefined callback function code_change/3 (behaviour 'gen_server')
%% logger.erl:2: Warning: undefined callback function handle_cast/2 (behaviour 'gen_server')
%% logger.erl:2: Warning: undefined callback function handle_info/2 (behaviour 'gen_server')
%% logger.erl:2: Warning: undefined callback function terminate/2 (behaviour 'gen_server')
%% {ok,logger}
%% 2> logger:start_link().
%% {ok,<0.85.0>}
%% 3> gen_server:call(logger, "hello world").
%% "hello world"
