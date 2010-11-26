from room import *
from deathRoom import *

class GateRoom(Room):
    def __init__(self):
        name = "GateRoom"
        description = """
You walk up to the big iron gate and see there's a handle.
"""
        default_result = "That doesn't seem sensible. I mean, the door's right there."
        super(GateRoom, self).__init__(name, description, default_result)

        actions = {"open it":
                       ("You open it and you are free!"
                        "\nThere are mountains. And berries! And..."
                        "\nOh, but then the bear comes with his katana and stabs you."
                        '\n"Who\'s laughing now!? Love this katana."',
                        DeathRoom())}
        self.add_actions(actions)
