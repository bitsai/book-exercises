from room import *
from end import *

class Start(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("choice", "result", 1)]
        next_room = End()
        super(Start, self).__init__(name, choices, next_room)
