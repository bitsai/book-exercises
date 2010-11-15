# and: logical AND
print (True and False)

# del: del(ete) binding
dummy = 1
del dummy

# from: specify module to import from
from sys import exit

# not: logical NOT
print (not True)

# while: while loop
while False:
    print "this should not print"

# as: used with 'import', rename imported object
#     used with 'with', bind return value
import sys as sys2
with open("ex37.txt") as f:
    print f.read()

# elif: else if
if 1 == 2:
    print "if"
elif 1 == 1:
    print "elif"
else:
    print "else"

# global: make variables global
global dummy2

# or: logical OR
print (True or False)

# with: encapsulate try-except-finally behavior by wrapping execution of a block with functionality provided by a guard object
with open("ex37.txt") as f:
    print f.read()

# assert: create assertion
assert 1 == 1

# else: specify default case after 'if' statement
# if: execute block if condition is True
if 1 != 1:
    print "if"
else:
    print "else"

# pass: null-op placeholder
def nothing():
    pass

# yield: exit generator and return a value
def gen():
    x = 11
    yield x

print gen().next()

# break: exit loop
while 1 == 1:
    break

# except: catch specified exception
try:
    f = open('not there.txt')
    print f.read()
except IOError:
    print "except"
finally:
    if f:
        f.close()

# import: import module or specific objects from module
from sys import exit

# print: print stuff
print "print"

# class: create class
class Pair:
    def __init__(self, first, second):
        self.first = first
        self.second = second

    def first(self):
        self.first

    def second(self):
        self.second

p = Pair(1, 2)
print p.first, p.second

# exec: exec(ute) python code
exec("print 'exec'")

# in: test whether the specified element is in the collection
print 2 in (1, 2, 3)
print 4 in (1, 2, 3)

# raise: raise/throw exception
class MyException(Exception):
    def __init__(self):
        pass

try:
    raise MyException
except MyException:
    print "raise"

# continue: continue loop without exiting
num = 0

while (num < 5):
   num += 1

   if (num % 2) == 0:
      continue

   print num

# finally: specify block that is always executed after try
try:
    f = open('not there.txt')
    print f.read()
except IOError:
    pass
finally:
    print "finally"

    if f:
        f.close()

# is: test object identity
print [] is []

# return: exit and return specified value(s)
# def: create function
def f():
    return "return"

print f()

# for: for loop
for x in ("for", "for", "for"):
    print x

# lambda: create anonymous function
f = lambda: "lambda"
print f()

# try: try a block of code, catch exceptions if necessary
try:
    print "try"
except Exception:
    print "except"
finally:
    print "finally"

print True
print False
print None
print "string"
print 1, 2, 3
print 1.0, 2.0, 3.0
print (1, 2, 3)

print "-\\-"
print "-\'-"
print "-\"-"
print "-\a-"
print "-\b-"
print "-\f-"
print "-\n-"
print "-\r-"
print "-\t-"
print "-\v-"

print "%d" % 10
print "%i" % 10
print "%o" % 10
print "%u" % 10
print "%x" % 10
print "%X" % 10
print "%e" % 10
print "%E" % 10
print "%f" % 10.0
print "%F" % 10.0
print "%g" % 10.0
print "%G" % 10.0
print "%c" % 'a'
print "%r" % 'a'
print "%s" % 'a'
print "%%"

# +: addition operator
# -: subtraction operator
# *: multiplication operator
# **: exponentiation operator
# /: division operator
# //: truncating division operator
# %: modulus operator
# <: less-than operator
# >: greater-than operator
# <=: less-or-equal operator
# >=: greater-or-equal operator
# ==: equal-to operator
# !=: not-equal operator
# <>: not-equal operator (obsolete)
# (): tuple (immutable!)
# []: list
# {}: dict(ionary)
# @: function decorator
# ,: list separator
# :: declare scope/block
# .: access object attribute/method
# =: assignment operator
# ;: create compound statements
# +=: increment operator
# -=: decrement operator
# *=: multiply-and-assign operator
# /=: divide-and-assign operator
# //=: truncated-divide-and-assign operator
# %=: modulus-and-assign operator
# **=: exponentiate-and-assign operator
