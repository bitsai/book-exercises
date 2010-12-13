from room import *

class Q10(Room):
    def __init__(self):
        name = self.__class__.__name__
        choices = [("A Landspeeder", "Wrong!", 0),
                   ("A Camel", "Wrong!", 0),
                   ("Banthas", "Wrong!", 0),
                   ("A Sail Barge", "Correct!", 1),
                   ("A Desert Yacht", "Wrong!", 0)]
        next_room = None
        super(Q10, self).__init__(name, choices, next_room)
