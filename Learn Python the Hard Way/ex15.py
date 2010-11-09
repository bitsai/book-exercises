# imports
from sys import argv

# unpack args
script, filename = argv

# open specified file
txt = open(filename)

# print filename arg
print "Here's your file %r:" % filename

# print file contents
#print txt.read()
#print txt.readline()
print txt.readlines()

# print prompt
print "I'll also ask you to type it again:"

# read new filename
file_again = raw_input("> ")

# open newly specified file
txt_again = open(file_again)

# print file contents
print txt_again.read()

txt.close()
txt_again.close()
