from room import *
from q7 import *

class Q6(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("1,000,000 KM/H", "Wrong!", 0),
                   ("Lightspeed", "Wrong!", 0),
                   ("Warp 8", "Wrong!", 0),
                   ("0.5 past Lightspeed", "Correct!", 1),
                   ("Warp 3.0", "Wrong!", 0)]
        next_room = Q7()
        super(Q6, self).__init__(name, choices, next_room)
