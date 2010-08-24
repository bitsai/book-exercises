CXX = g++
CC = g++
CXXFLAGS = -I. -I../chapter08

all:		grading_prog

Student_info.o:	Student_info.cc Student_info.h

grade.o:	grade.cc ../chapter08/median.h Student_info.h

grading_prog.o:	grading_prog.cc grade.h ../chapter08/median.h Student_info.h

grading_prog:	grading_prog.o grade.o Student_info.o

test:		all
	./grading_prog <../data/grades

clobber:
	rm -f *.o *.exe core grading_prog
