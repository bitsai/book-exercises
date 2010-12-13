from room import *
from q9 import *

class Q8(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("1", "Wrong!", 0),
                   ("2", "Correct!", 1),
                   ("3", "Wrong!", 0),
                   ("4", "Wrong!", 0),
                   ("6", "Wrong!", 0)]
        next_room = Q9()
        super(Q8, self).__init__(name, choices, next_room)
