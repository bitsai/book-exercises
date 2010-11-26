from room import *
from deathRoom import *
from gateRoom import *

class BearRoom(Room):
    def __init__(self):
        name = "BearRoom"
        description = """
Puzzled, you are about to pick up the fish poop diamond when
a bear bearing a load bearing sword walks in.
"Hey! That's my diamond! Where'd you get that!?"
It holds its paw out and looks at you.
"""
        default_result = "The bear looks puzzled as to why you'd do that."
        super(BearRoom, self).__init__(name, description, default_result)

        actions = {"give it":
                       ("The bear swipes at your hand to grab the diamond and"
                        "\nrips your hand off in the process. It then looks at"
                        '\nyour bloody stump and says, "Oh crap, sorry about that."'
                        "\nIt tries to put your hand back on, but you collapse."
                        "\nThe last thing you see is the bear shrug and eat you.",
                        DeathRoom()),
                   "say no":
                       ("The bear looks shocked. Nobody ever told a bear"
                        "\nwith a broadsword 'no'. It asks,"
                        '\n"Is it because it\'s not a katana? I could go get one!"'
                        "\nIt then runs off and now you notice a big iron gate."
                        '\n"Where the hell did that come from?" You say.',
                        GateRoom())}
        self.add_actions(actions)
