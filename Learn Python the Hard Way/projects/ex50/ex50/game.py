from start import *

def goto(room):
    print "\n--------\n"
    nextRoom = room.enter()
    goto(nextRoom)

goto(Start())
