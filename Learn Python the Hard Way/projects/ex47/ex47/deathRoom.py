from sys import exit
from random import randint

class DeathRoom(object):
    def __init__(self):
        self.name = "DeathRoom"
        self.quips = [
            "You died. You kinda suck at this.",
            "Your mom would be proud. If she were smarter.",
            "Such a luser.",
            "I have a small puppy that's better at this."]

    def enter(self):
        rand_quip_idx = randint(0, len(self.quips) - 1)
        print self.quips[rand_quip_idx]
        exit(1)
