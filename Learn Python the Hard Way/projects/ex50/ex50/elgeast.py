from page import *

class Elgeast(Page):
    def __init__(self):
        description = """
The Elgeast is a massive creature with razor-like teeth and an awesome hairdo! You watch as it gulps down several Maknarg mercilessly. In retrospect, you probably wouldn't have been able take one of the Maknarg on yourself. The Elgeast turn towards you but you are too small for it to see. That was a lucky escape!
"""

        choices = [("Return to Nourish", "nourish")]

        super(Elgeast, self).__init__("Elgeast", description, choices)
