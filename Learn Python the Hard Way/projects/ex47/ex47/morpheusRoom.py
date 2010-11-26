from room import *
from deathRoom import *
from princessRoom import *

class MorpheusRoom(Room):
    def __init__(self):
        name = "MorpheusRoom"
        description = """
Morpheus is seated before you.
He offers you two pills, a blue and a red.
"""
        default_result = "Morpheus just stares at you from behind his awesome shades."
        super(MorpheusRoom, self).__init__(name, description, default_result)

        actions = {"take blue pill":
                       ("You go on to lead a miserable, boring life."
                        "\nAnd die a miserable, boring death.",
                        DeathRoom()),
                   "take red pill":
                       ("Bizarreness ensues, and darkness descends...",
                        PrincessRoom())}
        self.add_actions(actions)
