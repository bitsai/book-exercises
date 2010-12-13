from room import *
from q10 import *

class Q9(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("Foo Stew", "Wrong!", 0),
                   ("Rootleaf", "Correct!", 1),
                   ("Tubor Soup", "Wrong!", 0),
                   ("Bantha Fodder", "Wrong!", 0),
                   ("'The Special'", "Wrong!", 0)]
        next_room = Q10()
        super(Q9, self).__init__(name, choices, next_room)
