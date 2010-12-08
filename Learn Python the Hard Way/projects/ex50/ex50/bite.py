from page import *

class Bite(Page):
    def __init__(self):
        description = """
You bite the enemy for 6 damage.

The enemy uses punch and hits for 3 damage.
"""

        choices = [("use shine", "useShine"),
                   ("bite!", "biteE")]

        super(Bite, self).__init__("Bite", description, choices)
