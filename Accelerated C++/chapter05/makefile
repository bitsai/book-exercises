CXX = g++
CC = g++

CXXFLAGS = -I../chapter04

all:		fails_vec1 fails_vec2 fails_iters fails_list \
		split_main split_main2 pics_main

median.o:	../chapter04/median.cc ../chapter04/median.h
	$(CXX) $(CXXFLAGS) -c ../chapter04/median.cc

Student_info.o:	../chapter04/Student_info.cc ../chapter04/Student_info.h
	$(CXX) $(CXXFLAGS) -c ../chapter04/Student_info.cc

grade.o:	grade.cc grade.h

ext_main.o:	ext_main.cc grade.h ../chapter04/Student_info.h

list_main.o:	list_main.cc grade.h ../chapter04/Student_info.h

fails_vec1.o:	fails_vec1.cc grade.h ../chapter04/Student_info.h

fails_vec2.o:	fails_vec2.cc grade.h ../chapter04/Student_info.h

fails_iters.o:	fails_iters.cc grade.h ../chapter04/Student_info.h

fails_list.o:	fails_list.cc grade.h  ../chapter04/Student_info.h

fails_vec1:	fails_vec1.o ext_main.o grade.o median.o Student_info.o

fails_vec2:	fails_vec2.o ext_main.o grade.o median.o Student_info.o

fails_iters:	fails_iters.o ext_main.o grade.o median.o Student_info.o

fails_list:	fails_list.o list_main.o grade.o median.o Student_info.o

pics.o:		pics.cc pics.h

pics_main.o:	pics_main.cc pics.h

pics_main:	pics_main.o pics.o

split.o:	split.cc split.h

split_main.o:	split_main.cc split.h

split_main2.o:	split_main2.cc split.h

split_main:	split_main.o split.o

split_main2:	split_main2.o split.o

test:		all
	./fails_vec1 <../data/grades
	./fails_vec2 <../data/grades
	./fails_iters <../data/grades
	./fails_list <../data/grades
	./split_main < split_main.cc
	./split_main2 < split_main2.cc
	./pics_main

clobber:
	rm -f *.o *.exe core fails_vec1 fails_vec2 fails_iters fails_list \
	split_main split_main2 pics_main
