from page import *

class ShineE(Page):
    def __init__(self):
        description = """
You use shine but before you can attack the enemy retaliates!

Luckily the attack misses and you have time to flee.

You devolve back into a simple microbe. You must now Evolve again.
"""

        choices = [("Evolve", "evolution")]

        super(ShineE, self).__init__("ShineE", description, choices)
