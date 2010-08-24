CXX = g++
CC = g++
CXXFLAGS = -I../chapter04 -I../chapter05

all:		split_main palin urls_main grade_analysis ext_fails1 ext_fails2

median.o:	../chapter04/median.cc ../chapter04/median.h
	$(CXX) $(CXXFLAGS) -c ../chapter04/median.cc

Student_info.o:	../chapter04/Student_info.cc ../chapter04/Student_info.h
	$(CXX) $(CXXFLAGS) -c ../chapter04/Student_info.cc

split.o:	split.cc ../chapter05/split.h

split_main.o:	../chapter05/split_main.cc ../chapter05/split.h
	$(CXX) $(CXXFLAGS) -c ../chapter05/split_main.cc

split_main:	split_main.o split.o

palin.o:	palin.cc

urls.o:		urls.cc urls.h

urls_main.o:	urls_main.cc urls.h

urls_main:	urls_main.o urls.o

grade.o:	grade.cc grade.h

grade_check.o:	grade_check.cc

analysis.o:	analysis.cc analysis.h

grade_analysis.o: grade_analysis.cc analysis.h

grade_analysis:	grade_analysis.o analysis.o grade_check.o \
		grade.o median.o Student_info.o

ext_main.o:	../chapter05/ext_main.cc grade.h ../chapter04/Student_info.h \
		../chapter04/median.h
	$(CXX) $(CXXFLAGS) -c ../chapter05/ext_main.cc

ext_fails1.o:	ext_fails1.cc grade.h ../chapter04/Student_info.h \
		../chapter04/median.h

ext_fails2.o:	ext_fails2.cc grade.h ../chapter04/Student_info.h \
		../chapter04/median.h

ext_fails1:	ext_main.o ext_fails1.o grade.o median.o Student_info.o

ext_fails2:	ext_main.o ext_fails2.o grade.o median.o Student_info.o

test:		all
	./split_main <split.cc
	./palin <../data/palins_input
	./urls_main <../data/urls.htm
	./grade_analysis <../data/grades
	./ext_fails1 <../data/grades
	./ext_fails2 <../data/grades

clobber:
	rm -f *.o *.exe core split_main palin urls_main grade_analysis \
	ext_fails1 ext_fails2
