from page import *

class UseShine(Page):
    def __init__(self):
        description = """
You blind yourself and in the chaos the enemy wins!
"""

        choices = [("restart", "evolution")]

        super(UseShine, self).__init__("UseShine", description, choices)
