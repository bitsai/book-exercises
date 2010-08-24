CXX = g++
CC = g++
CXXFLAGS = -I. -I../chapter11

all:	urls_main analysis main2 main3

Str.o:	Str.h Str.cc ../chapter11/Vec.h

Student_info.o:	Student_info.cc ../chapter11/Vec.h Str.h Student_info.h

grade.o:	grade.cc ../chapter11/Vec.h Str.h Student_info.h ../chapter11/median.h

urls_main.o:	urls_main.cc urls.h ../chapter11/Vec.h Str.h

urls.o:	urls.cc urls.h ../chapter11/Vec.h Str.h

urls_main: urls_main.o urls.o Str.o

analysis:       grade.o analysis.o Student_info.o grade_analysis.o grade_check.o Str.o

analysis.o:	analysis.cc ../chapter11/Vec.h Str.h Student_info.h ../chapter11/median.h

grade_analysis.o: grade_analysis.cc analysis.h

grade_check.o:  grade_check.cc

main2.o: main2.cc

main3.o: main3.cc grade.h ../chapter11/median.h Student_info.h

main2:   main2.o Str.o

main3:   main3.o grade.o Student_info.o Str.o

test:	./all
	./urls_main < ../data/urls.htm
	./analysis < ../data/grades
	./main2 < ../data/single_grade
	./main2 < ../data/grades
	./main3 < ../data/grades

clobber:
	rm -f *.o *.exe core urls_main analysis main2 main3
