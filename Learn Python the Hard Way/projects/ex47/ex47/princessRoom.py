from room import *
from deathRoom import *
from goldKoiRoom import *

class PrincessRoom(Room):
    def __init__(self):
        name = "PrincessRoom"
        description = """
You see a beautiful Princess with a shiny crown.
She offers you some cake.
"""
        default_result = "The princess looks at you confused and just points a the cake."
        super(PrincessRoom, self).__init__(name, description, default_result)

        actions = {"eat it":
                       ("You explode like a pinata full of frogs."
                        "\nThe Princess cackles and eats the frogs. Yum!",
                        DeathRoom()),
                   "do not eat it":
                       ("She throws the cake at you and it cuts off your head."
                        "\nThe last thing you see is her munching on your torso. Yum!",
                        DeathRoom()),
                   "make her eat it":
                       ("The Princess screams as you cram the cake in her mouth."
                        "\nThen she smiles and cries and thanks you for saving her."
                        "\nShe points to a tiny door and says, 'The Koi needs cake too.'"
                        "\nShe gives you the very last bit of cake and shoves you in.",
                        GoldKoiRoom())}
        self.add_actions(actions)
