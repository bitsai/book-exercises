from room import *
from q8 import *

class Q7(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("Janson", "Wrong!", 0),
                   ("Dodoona", "Wrong!", 0),
                   ("Tatooine", "Wrong!", 0),
                   ("Alderaan", "Correct!", 1),
                   ("Banthas", "Wrong!", 0)]
        next_room = Q8()
        super(Q7, self).__init__(name, choices, next_room)
