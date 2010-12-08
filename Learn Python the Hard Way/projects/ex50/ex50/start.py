from page import *

class Start(Page):
    def __init__(self):
        description = """
And suddenly you are. You have no eyes to open, you have nothing except some pincers. You just are. In a millisecond, you, an insignificant single cell, have been born. Right now you lay in a drop of water.
"""

        choices = [("Nourish", "nourish")]

        super(Start, self).__init__("Start", description, choices)
