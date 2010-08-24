SHELL=/bin/sh
CXX = g++
CC = g++

SUBS = chapter00 chapter01 chapter02 chapter03 chapter04 \
	chapter05 chapter06 chapter07 chapter08 chapter09 \
	chapter10 chapter11 chapter12 chapter13 chapter14 \
	chapter15 chapter16

all:
	-for c in $(SUBS); do echo "=== $$c =="; (cd $$c; $(MAKE) "CXX=$(CXX)" "CC=$(CC)") done

test:
	-for c in $(SUBS); do echo "=== $$c =="; (cd $$c; $(MAKE) "CXX=$(CXX)" "CC=$(CC)" test); done

clobber:
	-for c in $(SUBS); do echo "=== $$c =="; (cd $$c; $(MAKE) "CXX=$(CXX)" "CC=$(CC)" clobber);done
