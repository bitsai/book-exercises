from page import *

class BiteAwayE(Page):
    def __init__(self):
        description = """
You bite the enemy and he loses focus! You deal 3 damage.
"""

        choices = [("bite!!", "biteEE"),
                   ("use shine", "useShine")]

        super(BiteAwayE, self).__init__("BiteAwayE", description, choices)
