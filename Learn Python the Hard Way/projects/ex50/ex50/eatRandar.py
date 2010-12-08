from page import *

class EatRandar(Page):
    def __init__(self):
        description = """
A Randar is a small creature that passes electrical currents thoughout its body.

But you don't know that until you bite down upon its skin, making you and the Randar explode with 500 volts running through you both.

Other creatures will soon feast upon the chunks of your bodies floating around.
"""

        choices = [("Restart", "evolution")]

        super(EatRandar, self).__init__("EatRandar", description, choices)
