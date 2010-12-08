from page import *

class EatMaknargQ(Page):
    def __init__(self):
        description = """
The weaker Maknarg tries to flee but it is soon devoured by your mighty pincers.
"""

        choices = [("eat Randar", "eatRandar"),
                   ("eat Greaple", "eatGreaple"),
                   ("eat Elgeast", "eatElgeast"),
                   ("split", "split")]

        super(EatMaknargQ, self).__init__("EatMaknargQ", description, choices)
