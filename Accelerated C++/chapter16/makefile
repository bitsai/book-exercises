CXX = g++
CC = g++

all:		histo

histo.o:	histo.cc Pic.h Core.h median.h

Pic.o:  	Pic.cc Pic.h
	$(CC) $(CXXFLAGS) -c Pic.cc

Core.o:		Core.cc Core.h
	$(CC) $(CXXFLAGS) -c Core.cc

Student_info.o: Student_info.cc median.h Student_info.h Core.h
	$(CC) $(CXXFLAGS) -c Student_info.cc

grade.o:        grade.cc median.h Student_info.h
	$(CC) $(CXXFLAGS) -c grade.cc

read_hw.o:      read_hw.cc median.h Student_info.h
	$(CC) $(CXXFLAGS) -c read_hw.cc


histo:		histo.o Pic.o Core.o Student_info.o read_hw.o grade.o

test:		all
	./histo <../data/kids_grades

clobber:
	rm -f *.o *.exe core histo
