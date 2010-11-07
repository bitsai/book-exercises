x = "There are %d types of people." % 10
binary = "binary"
do_not = "don't"

# string inside a string #1, #2
y = "Those who know %s and those who %s." % (binary, do_not)

print x
print y

# string inside a string #3
print "I said: %r." % x

# string inside a string #4
print "I also said: '%s'." % y

hilarious = False
joke_evaluation = "Isn't that joke so funny?! %r"

print joke_evaluation % hilarious

w = "This is the left side of..."
e = "a string with a right side."

print w + e
