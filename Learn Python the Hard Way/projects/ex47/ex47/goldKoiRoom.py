from room import *
from deathRoom import *
from bearRoom import *

class GoldKoiRoom(Room):
    def __init__(self):
        name = "GoldKoiRoom"
        description = """
There is a garden with a koi pond in the center.
You walk close and see a massive fin poke out.
You peek in and a creepy looking huge Koi stares at you.
It opens its mouth waiting for food.
"""
        default_result = "The Koi gets annoyed and wiggles a bit."
        super(GoldKoiRoom, self).__init__(name, description, default_result)

        actions = {"feed it":
                       ("The Koi jumps up, and rather than eating the cake, eats your arm."
                        "\nYou fall in and the Koi shrugs then eats you."
                        "\nYou are then pooped out sometime later.",
                        DeathRoom()),
                   "do not feed it":
                       ("The Koi grimaces, then thrashes around for a second."
                        "\nIt rushes to the other end of the pond, braces against the wall..."
                        "\nthen it *lunges* out of the water, up in the air and over your"
                        "\nentire body, cake and all."
                        "\nYou are then pooped out a week later.",
                        DeathRoom()),
                   "throw it in":
                       ("The Koi wiggles, then leaps into the air to eat the cake."
                        "\nYou can see it's happy, it then grunts, thrashes..."
                        "\nand finally rolls over and poops a magic diamond into the air"
                        "\nat your feet.",
                        BearRoom())}
        self.add_actions(actions)
