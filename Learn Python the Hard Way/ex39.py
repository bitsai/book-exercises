ten_things = "Apples Oranges Crows Telephone Light Sugar"

print "Wait there's not 10 things in that list, let's fix that."

stuff = ten_things.split(' ') # split(ten_things, ' ')
more_stuff = ["Day", "Night", "Song", "Frisbee", "Corn", "Banana", "Girl", "Boy"]

while len(stuff) != 10:
    next_one = more_stuff.pop() # pop(more_stuff)
    print "Adding: ", next_one
    stuff.append(next_one) # append(stuff, next_one)
    print "There's %d items now." % len(stuff)

print "There we go: ", stuff

print "Let's do some things with stuff."

print stuff[1]
print stuff[-1] # whoa! fancy
print stuff.pop() # pop(stuff)
print ' '.join(stuff) # what? cool! - join(' ', stuff)
print '#'.join(stuff[3:5]) # super stellar! - join('#', stuff[3:5])

print dir(stuff)
