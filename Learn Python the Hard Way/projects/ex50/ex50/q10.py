from room import *

class Q10(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("Because it's green like him", "Wrong!", 0),
                   ("Because he liked to swim in it", "Wrong!", 0),
                   ("Because he kept goldfish in it", "Wrong!", 0),
                   ("Because it helped him hide from the Emperor and Death Vader", "Correct!", 1),
                   ("Because he was friends with Swamp Thing", "Wrong!", 0)]
        next_room = None
        super(Q10, self).__init__(name, choices, next_room)
