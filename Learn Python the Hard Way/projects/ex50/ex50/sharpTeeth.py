from page import *

class SharpTeeth(Page):
    def __init__(self):
        description = """
Your mission becomes a quest to become top of the food chain.

Your first oppenent will be the wirefi!
"""

        choices = [("use bite", "useBite"),
                   ("use shine", "useShine")]

        super(SharpTeeth, self).__init__("SharpTeeth", description, choices)
