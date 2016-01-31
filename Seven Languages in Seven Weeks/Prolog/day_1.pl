author_of(the_name_of_the_wind, patrick_rothfuss).
author_of(enders_game, orson_scott_card).
author_of(anathem, neil_stephenson).
author_of(hyperion, dan_simmons).
author_of(american_gods, neil_gaiman).
author_of(the_blade_itself, joe_abercrombie).
author_of(the_atrocity_archives, charles_stross).
author_of(lions_of_al_rassan, guy_gavriel_kay).
author_of(dune, frank_herbert).
author_of(a_game_of_thrones, george_r_r_martin).

%% | ?- author_of(X, patrick_rothfuss).

%% X = the_name_of_the_wind ? a

%% no

instrument_of(miles_davis, trumpet).
instrument_of(miles_davis, flugelhorn).
instrument_of(miles_davis, piano).
instrument_of(miles_davis, organ).
instrument_of(miles_davis, synthesizer).
instrument_of(dave_grohl, guitar).
instrument_of(dave_grohl, vocals).
instrument_of(dave_grohl, drums).
instrument_of(chris_cornell, vocals).
instrument_of(chris_cornell, guitar).

genre_of(miles_davis, jazz).
genre_of(dave_grohl, rock).
genre_of(chris_cornell, rock).

%% | ?- instrument_of(X, guitar).

%% X = dave_grohl ? a

%% X = chris_cornell

%% yes
