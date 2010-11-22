from deathRoom import *
from bearRoom import *

class GoldKoiRoom(object):
    def enter(self):
        print "There is a garden with a koi pond in the center."
        print "You walk close and see a massive fin poke out."
        print "You peek in and a creepy looking huge Koi stares at you."
        print "It opens its mouth waiting for food."

        action = raw_input("> ")

        if action == "feed it":
            print "The Koi jumps up, and rather than eating the cake, eats your arm."
            print "You fall in and the Koi shrugs then eats you."
            print "You are then pooped out sometime later."
            return DeathRoom()

        elif action == "do not feed it":
            print "The Koi grimaces, then thrashes around for a second."
            print "It rushes to the other end of the pond, braces against the wall..."
            print "then it *lunges* out of the water, up in the air and over your"
            print "entire body, cake and all."
            print "You are then pooped out a week later."
            return DeathRoom()

        elif action == "throw it in":
            print "The Koi wiggles, then leaps into the air to eat the cake."
            print "You can see it's happy, it then grunts, thrashes..."
            print "and finally rolls over and poops a magic diamond into the air"
            print "at your feet."
            return BearRoom()

        else:
            print "The Koi gets annoyed and wiggles a bit."
            return self
