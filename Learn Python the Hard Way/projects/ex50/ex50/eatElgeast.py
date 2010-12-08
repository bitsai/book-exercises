from page import *

class EatElgeast(Page):
    def __init__(self):
        description = """
You turn to face an Elgeast and realise that it is almost forty-five times bigger than you.

You're not scared, though. You won't be scared. You can't be scared. You can't do anything, except Restart.
"""

        choices = [("Restart", "evolution")]

        super(EatElgeast, self).__init__("EatElgeast", description, choices)
