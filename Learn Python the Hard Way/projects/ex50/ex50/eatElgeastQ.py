from page import *

class EatElgeastQ(Page):
    def __init__(self):
        description = """
You turn to face an Elgeast and realise that it is almost thirty times bigger than you.

You're not scared, though. You won't be scared. You can't be scared. You can't do anything, except Restart.
"""

        choices = [("Restart", "evolution")]

        super(EatElgeastQ, self).__init__("EatElgeastQ", description, choices)
