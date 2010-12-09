from start import *

class Engine(object):
    def goto(self, room):
        print "\n--------\n"
        nextRoom = room.enter()
        self.goto(nextRoom)

e = Engine()
e.goto(Start())
