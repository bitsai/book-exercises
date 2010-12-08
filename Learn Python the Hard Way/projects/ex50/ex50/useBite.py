from page import *

class UseBite(Page):
    def __init__(self):
        description = """
You hit the enemy for 5 damage!

The enemy uses fire and defeats 6 of your people!
"""

        choices = [("bite", "bite"),
                   ("use shine", "useShine")]

        super(UseBite, self).__init__("UseBite", description, choices)
