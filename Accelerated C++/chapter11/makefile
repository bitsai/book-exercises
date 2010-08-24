CXX = g++
CC = g++

all:		urls_main analysis main2 main3

Student_info.o:	Student_info.cc Vec.h Student_info.h

grade.o:	grade.cc Vec.h Student_info.h median.h

urls_main.o:	urls_main.cc urls.h Vec.h

urls.o:		urls.cc urls.h Vec.h

urls_main:	urls_main.o urls.o

analysis:       analysis.o grade.o Student_info.o grade_analysis.o grade_check.o

analysis.o:	analysis.cc Vec.h Student_info.h median.h

grade_analysis.o: grade_analysis.cc analysis.h

grade_check.o:  grade_check.cc

main2.o: 	main2.cc

main3.o: 	main3.cc grade.h median.h Student_info.h

main2:   	main2.o

main3:   	main3.o grade.o Student_info.o

test:		all
	./urls_main < ../data/urls.htm
	./analysis < ../data/grades
	./main2 < ../data/single_grade
	./main2 < ../data/grades
	./main3 < ../data/grades

clobber:
	rm -f *.o *.exe core urls_main analysis main2 main3
