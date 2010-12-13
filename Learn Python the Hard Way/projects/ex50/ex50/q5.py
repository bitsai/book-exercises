from room import *
from q6 import *

class Q5(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("Green", "Correct!", 1),
                   ("Yellow", "Wrong!", 0),
                   ("Blue", "Wrong!", 0),
                   ("Red", "Wrong!", 0),
                   ("Purple", "Wrong!", 0)]
        next_room = Q6()
        super(Q5, self).__init__(name, choices, next_room)
