from room import *
from q5 import *

class Q4(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("Banthas", "Correct!", 1),
                   ("Maranthas", "Wrong!", 0),
                   ("Mantes", "Wrong!", 0),
                   ("Bespins", "Wrong!", 0),
                   ("Sand Creatures", "Wrong!", 0)]
        next_room = Q5()
        super(Q4, self).__init__(name, choices, next_room)
