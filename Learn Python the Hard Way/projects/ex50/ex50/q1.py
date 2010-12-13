from room import *
from q2 import *

class Q1(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("Kashyyk", "Wrong!", 0),
                   ("Death Star", "Wrong!", 0),
                   ("Alderaan", "Wrong!", 0),
                   ("Tatooine", "Wrong!", 0),
                   ("Dagobah", "Correct!", 1)]
        next_room = Q2()
        super(Q1, self).__init__(name, choices, next_room)
