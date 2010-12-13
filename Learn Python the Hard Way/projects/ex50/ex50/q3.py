from room import *
from q4 import *

class Q3(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("To destroy the Death Star", "Wrong!", 0),
                   ("To save his friends at Bespin", "Correct!", 1),
                   ("To start his own Jedi Academy", "Wrong!", 0),
                   ("To marry Princess Leia", "Wrong!", 0),
                   ("To get fried chicken", "Wrong!", 0)]
        next_room = Q4()
        super(Q3, self).__init__(name, choices, next_room)
