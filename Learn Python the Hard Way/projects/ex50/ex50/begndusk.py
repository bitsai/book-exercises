from page import *

class Begndusk(Page):
    def __init__(self):
        description = """
The Begndusk doesn't atempt to fight back, due to the fact that it's an herbivore and has no pincers. By eating the Begndusk you have grown bigger and can digest plant-life.
"""

        choices = [("eat Maknarg?", "eatMaknargQ"),
                   ("eat Elgeast?", "eatElgeastQ"),
                   ("eat Greaple (a form of plant)?", "eatGreapleQ")]

        super(Begndusk, self).__init__("Begndusk", description, choices)
