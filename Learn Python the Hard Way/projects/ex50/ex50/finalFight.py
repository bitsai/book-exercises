from page import *

class FinalFight(Page):
    def __init__(self):
        description = """
The final enemy is the bloctapus. If you defeat this enemy you will be the top of the food chain!
"""

        choices = [("use shine", "useShine"),
                   ("bite away!", "biteAwayE")]

        super(FinalFight, self).__init__("FinalFight", description, choices)
