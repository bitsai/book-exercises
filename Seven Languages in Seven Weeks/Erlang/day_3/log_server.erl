-module(log_server).
-behaviour(gen_server).

-export([init/1, start_link/0, handle_call/3]).

init(_Args) ->
    {ok, logging}.

start_link() ->
    gen_server:start_link({local, log_server}, log_server, [], []).

handle_call(Msg, _From, State) ->
    Formatted = io_lib:format("~s\n", [Msg]),
    file:write_file("/tmp/log.txt", Formatted, [append]),
    {reply, Msg, State}.

%% 1> c(log_server).
%% log_server.erl:2: Warning: undefined callback function code_change/3 (behaviour 'gen_server')
%% log_server.erl:2: Warning: undefined callback function handle_cast/2 (behaviour 'gen_server')
%% log_server.erl:2: Warning: undefined callback function handle_info/2 (behaviour 'gen_server')
%% log_server.erl:2: Warning: undefined callback function terminate/2 (behaviour 'gen_server')
%% {ok,log_server}
%% 2> log_server:start_link().
%% {ok,<0.85.0>}
%% 3> gen_server:call(log_server, "hello world").
%% "hello world"
