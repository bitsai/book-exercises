from room import *
from q6 import *

class Q5(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("Great shot, kid!", "Correct!", 1),
                   ("OK, let's go home.", "Wrong!", 0),
                   ("Sorry about the mess, kid.", "Wrong!", 0),
                   ("That's one!", "Wrong!", 0),
                   ("Our job is done.", "Wrong!", 0)]
        next_room = Q6()
        super(Q5, self).__init__(name, choices, next_room)
