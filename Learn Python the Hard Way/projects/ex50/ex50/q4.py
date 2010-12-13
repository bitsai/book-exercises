from room import *
from q5 import *

class Q4(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("Darth Vader", "Correct!", 1),
                   ("Princess Leia", "Wrong!", 0),
                   ("Han Solo", "Wrong!", 0),
                   ("Chewbacca", "Wrong!", 0),
                   ("Darth Maul", "Wrong!", 0)]
        next_room = Q5()
        super(Q4, self).__init__(name, choices, next_room)
