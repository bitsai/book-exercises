from page import *

class EatSlizeox(Page):
    def __init__(self):
        description = """
The Slizeox is a plant that has a putrid taste.

But like most horrible-tasting food, it's good for you!

You have now grown hands!

You now have the power to turn too bright to look at for short periods of time!
"""

        choices = [("Evolve!", "evolveE")]

        super(EatSlizeox, self).__init__("EatSlizeox", description, choices)
