CXX = g++
CC = g++

all:		main1 main2 main3

Student_info.o:	Student_info.cc Student_info.h

grade.o:	grade.cc grade.h median.h Student_info.h

main3.o:	main3.cc grade.h median.h Student_info.h

median.o:	median.cc median.h

main3:		main3.o grade.o median.o Student_info.o

test:		all
	./main1 <../data/single_grade
	./main2 <../data/single_grade
	./main2 <../data/grades
	./main3 <../data/grades

clobber:
	rm -f *.o *.exe core main1 main2 main3
