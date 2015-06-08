-module(translate_server).
-behavior(gen_server).

-export([init/1, start_link/0, handle_call/3]).

init(_Args) ->
    {ok, translating}.

start_link() ->
    gen_server:start_link({global, translater}, translate_server, [], []).

handle_call(Msg, _From, State) ->
    Translated = case Msg of
                     "casa" -> "house";
                     "blanca" -> "white";
                     "muerte" -> "death";
                     _ -> "I don't understand"
                 end,
    {reply, Translated, State}.

%% (start shell 'a')
%% erl -sname a
%% (a@LA-MBP-403)1> c(translate_server).
%% translate_server.erl:2: Warning: undefined callback function code_change/3 (behaviour 'gen_server')                                                                            
%% translate_server.erl:2: Warning: undefined callback function handle_cast/2 (behaviour 'gen_server')                                                                            
%% translate_server.erl:2: Warning: undefined callback function handle_info/2 (behaviour 'gen_server')                                                                            
%% translate_server.erl:2: Warning: undefined callback function terminate/2 (behaviour 'gen_server')                                                                              
%% {ok,translate_server}
%% (a@LA-MBP-403)2> translate_server:start_link().
%% {ok,<0.45.0>}

%% (start shell 'b')
%% erl -sname b
%% (b@LA-MBP-403)1> net_kernel:connect_node('a@LA-MBP-403').
%% true
%% (b@LA-MBP-403)2> gen_server:call({global, translater}, "casa").
%% "house"
