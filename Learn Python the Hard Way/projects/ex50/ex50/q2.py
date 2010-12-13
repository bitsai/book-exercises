from room import *
from q3 import *

class Q2(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("Darth Vader", "Wrong!", 0),
                   ("R2-D2", "Correct!", 1),
                   ("Han Solo", "Wrong!", 0),
                   ("Chewbacca", "Wrong!", 0),
                   ("C-3PO", "Wrong!", 0)]
        next_room = Q3()
        super(Q2, self).__init__(name, choices, next_room)
