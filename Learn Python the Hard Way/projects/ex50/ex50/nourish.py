from page import *

class Nourish(Page):
    def __init__(self):
        description = """
Nourishing is basically hunting for cells. It's survival of the fittest now, and there are several different species in the drop of water with you. A power your species already possesses is to take on skills of other cells. These skills will help you evolve.

Consume:
"""

        choices = [("Maknarg", "maknarg"),
                   ("Elgeast", "elgeast"),
                   ("Begndusk", "begndusk")]

        super(Nourish, self).__init__("Nourish", description, choices)
