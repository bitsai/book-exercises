CXX = g++
CC = g++
CXXFLAGS = -I. -I../chapter08

all:		std_main1 std_main2 std_main3 urls_main

std_main1:	std_main1.o Student_info.o Core.o grade.o read_hw.o

std_main1.o:	std_main1.cc Student_info.h Handle.h

std_main2:	std_main2.o Student_info.o Core.o grade.o read_hw.o

std_main2.o:	std_main2.cc Student_info.h Ref_handle.h

std_main3:	std_main3.o Student_info.o Core.o grade.o read_hw.o

std_main3.o:	std_main3.cc Student_info.h Ptr.h

Core.o:	Core.cc Core.h

Student_info.o:	Student_info.cc Core.h Student_info.h

grade.o:	grade.cc Student_info.h

read_hw.o:	read_hw.cc

urls_main:	urls_main.o urls.o Vec_clone.o Str.o

urls_main.o:	urls_main.cc urls.h Vec.h Str.h

urls.o:	urls.cc urls.h Vec.h Str.h

Vec_clone.o:	Vec_clone.cc Vec.h

Str.o:  Str.cc Str.h

test:		all
	./std_main1 <../data/mixed_grades
	./std_main2 <../data/mixed_grades
	./std_main3 <../data/mixed_grades
	./urls_main <../data/urls.htm

clobber:
	rm -f *.o *.exe core std_main1 std_main2 std_main3 urls_main
