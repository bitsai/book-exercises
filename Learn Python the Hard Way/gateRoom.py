from deathRoom import *

class GateRoom(object):
    def enter(self):
        print "You walk up to the big iron gate and see there's a handle."

        action = raw_input("> ")

        if action == 'open it':
            print "You open it and you are free!"
            print "There are mountains. And berries! And..."
            print "Oh, but then the bear comes with his katana and stabs you."
            print '"Who\'s laughing now!? Love this katana."'
            return DeathRoom()

        else:
            print "That doesn't seem sensible. I mean, the door's right there."
            return self
