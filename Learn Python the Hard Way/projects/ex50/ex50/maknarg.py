from page import *

class Maknarg(Page):
    def __init__(self):
        description = """
The being is slightly bigger than you. He gulps you up!
"""

        choices = [("Restart", "evolution")]

        super(Maknarg, self).__init__("Maknarg", description, choices)
