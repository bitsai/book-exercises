from page import *

class Evolution(Page):
    def __init__(self):
        description = """
This is where life begins for you. Millions of generations of species will be determined by the choices you make. This is a game of survival.
"""

        choices = [("Start", "start")]

        super(Evolution, self).__init__("Evolution", description, choices)
