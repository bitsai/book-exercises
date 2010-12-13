from room import *
from q8 import *

class Q7(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("The Emperor", "Wrong!", 0),
                   ("Princess Leia", "Wrong!", 0),
                   ("Darth Vader", "Wrong!", 0),
                   ("The ghost of Obi-Wan Kenobi", "Correct!", 1),
                   ("Qui-Gon Jinn", "Wrong!", 0)]
        next_room = Q8()
        super(Q7, self).__init__(name, choices, next_room)
