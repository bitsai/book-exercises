from start import *

def end(score):
    print "\n--------\n"
    print "You got", score, "correct."
    ending_filename = "descriptions/"

    if score in range(4):
        ending_filename += "bad_ending.txt"
    elif score in range(5, 10):
        ending_filename += "ok_ending.txt"
    else:
        ending_filename += "good_ending.txt"

    with open(ending_filename) as f:
        print "\n", f.read()

def goto(room, score):
    print "\n--------\n"
    next_room, new_score = room.enter(score)
    if next_room is None:
        end(new_score)
    else:
        goto(next_room, new_score)

goto(Start(), 0)
