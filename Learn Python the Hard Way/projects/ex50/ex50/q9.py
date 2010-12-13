from room import *
from q10 import *

class Q9(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("A desert", "Wrong!", 0),
                   ("A swamp", "Correct!", 1),
                   ("A cave", "Wrong!", 0),
                   ("A mountain", "Wrong!", 0),
                   ("A farm", "Wrong!", 0)]
        next_room = Q10()
        super(Q9, self).__init__(name, choices, next_room)
