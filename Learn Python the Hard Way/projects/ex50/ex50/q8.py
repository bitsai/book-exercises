from room import *
from q9 import *

class Q8(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("Frostbite", "Wrong!", 0),
                   ("Old age", "Correct!", 1),
                   ("Force lightning", "Wrong!", 0),
                   ("Force choke", "Wrong!", 0),
                   ("Frustration with Luke Skywalker", "Wrong!", 0)]
        next_room = Q9()
        super(Q8, self).__init__(name, choices, next_room)
