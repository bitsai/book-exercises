from deathRoom import *
from gateRoom import *

class BearRoom(object):
    def enter(self):
        print "Puzzled, you are about to pick up the fish poop diamond when"
        print "a bear bearing a load bearing sword walks in."
        print 'Hey! That\'s my diamond! Where\'d you get that!?"'
        print "It holds its paw out and looks at you."

        action = raw_input("> ")

        if action == "give it":
            print "The bear swipes at your hand to grab the diamond and"
            print "rips your hand off in the process. It then looks at"
            print 'your bloody stump and says, "Oh crap, sorry about that."'
            print "It tries to put your hand back on, but you collapse."
            print "The last thing you see is the bear shrug and eat you."
            return DeathRoom()

        elif action == "say no":
            print "The bear looks shocked. Nobody ever told a bear"
            print "with a broadsword 'no'. It asks,"
            print '"Is it because it\'s not a katana? I could go get one!"'
            print "It then runs off and now you notice a big iron gate."
            print '"Where the hell did that come from?" You say.'
            return GateRoom()

        else:
            print "The bear looks puzzled as to why you'd do that."
            return self
