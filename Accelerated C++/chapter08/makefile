CXX = g++
CC = g++
CXXFLAGS = -I. -I../chapter04

all:		split_main main3 analysis

Student_info.o:	../chapter04/Student_info.cc ../chapter04/Student_info.h
	$(CXX) $(CXXFLAGS) -c ../chapter04/Student_info.cc

split_main.o:	split_main.cc ../chapter05/split.h

split_main:	split_main.o

analysis.o:	../chapter06/analysis.cc ../chapter06/analysis.h
	$(CXX) $(CXXFLAGS) -c ../chapter06/analysis.cc

grade.o:	../chapter06/grade.cc ../chapter06/grade.h
	$(CXX) $(CXXFLAGS) -c ../chapter06/grade.cc

main3.o:	../chapter04/main3.cc ../chapter06/grade.h
	$(CXX) $(CXXFLAGS) -c ../chapter04/main3.cc

main3:		main3.o grade.o analysis.o Student_info.o

analysis:	grade.o analysis.o Student_info.o grade_analysis.o grade_check.o

grade_analysis.o: ../chapter06/grade_analysis.cc ../chapter06/analysis.h
	$(CXX) $(CXXFLAGS) -c ../chapter06/grade_analysis.cc

grade_check.o:	../chapter06/grade_check.cc
	$(CXX) $(CXXFLAGS) -c ../chapter06/grade_check.cc

test:		all
	./split_main <split_main.cc
	./main3 < ../data/single_grade
	./main3 < ../data/grades
	./analysis < ../data/grades

clobber:
	rm -f *.o *.exe core split_main main3 analysis
