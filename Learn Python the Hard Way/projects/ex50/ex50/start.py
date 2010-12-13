from room import *
from q1 import *

class Start(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("Continue", "'Ho ho ho, let the game begin!' booms Jabba...", 0)]
        next_room = Q1()
        super(Start, self).__init__(name, choices, next_room)
