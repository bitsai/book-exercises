from room import *
from q4 import *

class Q3(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("R2-D2", "Wrong!", 0),
                   ("C-3PO", "Correct!", 1),
                   ("Obi-Wan Kenobi", "Wrong!", 0),
                   ("Yoda", "Wrong!", 0),
                   ("Darth Vader", "Wrong!", 0)]
        next_room = Q4()
        super(Q3, self).__init__(name, choices, next_room)
