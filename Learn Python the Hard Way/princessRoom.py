from deathRoom import *
from goldKoiRoom import *

class PrincessRoom(object):
    def enter(self):
        print "You see a beautiful Princess with a shiny crown."
        print "She offers you some cake."

        action = raw_input("> ")
       
        if action == "eat it":
            print "You explode like a pinata full of frogs."
            print "The Princess cackles and eats the frogs. Yum!"
            return DeathRoom()

        elif action == "do not eat it":
            print "She throws the cake at you and it cuts off your head."
            print "The last thing you see is her munching on your torso. Yum!"
            return DeathRoom()

        elif action == "make her eat it":
            print "The Princess screams as you cram the cake in her mouth."
            print "Then she smiles and cries and thanks you for saving her."
            print "She points to a tiny door and says, 'The Koi needs cake too.'"
            print "She gives you the very last bit of cake and shoves you in."
            return GoldKoiRoom()

        else:
            print "The princess looks at you confused and just points a the cake."
            return self
