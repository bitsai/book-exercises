from room import *
from end import *

class Start(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("choice", "consequence", End())]
        super(Start, self).__init__(name, choices)
