from room import *
from q7 import *

class Q6(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("Imperial Star Destroyer", "Wrong!", 0),
                   ("Alderaan", "Wrong!", 0),
                   ("Tatooine", "Wrong!", 0),
                   ("Dagobah", "Correct!", 1),
                   ("Death Star", "Wrong!", 0)]
        next_room = Q7()
        super(Q6, self).__init__(name, choices, next_room)
