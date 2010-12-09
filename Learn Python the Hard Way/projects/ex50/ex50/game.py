from start import *

def goto(room, score):
    print "\n--------\n"
    next_room, new_score = room.enter(score)
    goto(next_room, new_score)

goto(Start(), 0)
