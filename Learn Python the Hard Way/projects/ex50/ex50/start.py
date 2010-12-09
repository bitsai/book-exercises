from room import *

class Start(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = []
        super(Start, self).__init__(name, choices)
