from page import *

class EatGreapleQ(Page):
    def __init__(self):
        description = """
You try to eat the Greaple when it starts to shine brightly. The heat that plant emits is too strong for your tiny body to come close.
"""

        choices = [("eat something else", "begndusk")]

        super(EatGreapleQ, self).__init__("EatGreapleQ", description, choices)
