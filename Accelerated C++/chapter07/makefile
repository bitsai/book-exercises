CXX = g++
CC = g++
CXXFLAGS = -I../chapter05

all:		wc xref grammar

wc.o:		wc.cc

wc:		wc.o

xref.o:		xref.cc ../chapter05/split.h

xref:		xref.o split.o

grammar.o:	grammar.cc ../chapter05/split.h

split.o:	../chapter06/split.cc ../chapter05/split.h
	$(CXX) $(CXXFLAGS) -c ../chapter06/split.cc

grammar:	grammar.o split.o

test:		all
	./wc < wc.cc
	./xref < wc.cc
	./grammar <../data/grammar_input

clobber:
	rm -f *.o *.exe core wc xref grammar
