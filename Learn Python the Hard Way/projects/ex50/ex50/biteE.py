from page import *

class BiteE(Page):
    def __init__(self):
        description = """
You bite the enemy for 6 damage.

The enemy uses punch but trips and loses 3 health.
"""

        choices = [("Final fight", "finalFight")]

        super(BiteE, self).__init__("BiteE", description, choices)
