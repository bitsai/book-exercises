from room import *
from q2 import *

class Q1(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("IX-88", "Wrong!", 0),
                   ("CPO Sharkey", "Wrong!", 0),
                   ("Chewbacca", "Wrong!", 0),
                   ("The Man", "Wrong!", 0),
                   ("C-3PO", "Correct!", 1)]
        next_room = Q2()
        super(Q1, self).__init__(name, choices, next_room)
