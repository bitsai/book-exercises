from page import *

class BiteEE(Page):
    def __init__(self):
        description = """
You bite the enemy and he loses 4 hp.

The enemy uses mana-blast and 10 of your speices die.
"""

        choices = [("shine!", "shineE")]

        super(BiteEE, self).__init__("BiteEE", description, choices)
