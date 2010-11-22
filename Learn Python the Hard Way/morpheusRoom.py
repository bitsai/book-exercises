from deathRoom import *
from princessRoom import *

class MorpheusRoom(object):
    def enter(self):
        print "Morpheus is seated before you."
        print "He offers you two pills, a blue and a red."

        action = raw_input("> ")

        if action == "take red pill":
            print "Bizarreness ensues, and darkness descends..."
            return PrincessRoom()

        elif action == "take blue pill":
            print "You go on to lead a miserable, boring life."
            print "And die a miserable, boring death."
            return DeathRoom()

        else:
            print "Morpheus just stares at you from behind his awesome shades."
            return self
