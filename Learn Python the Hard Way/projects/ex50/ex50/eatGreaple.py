from page import *

class EatGreaple(Page):
    def __init__(self):
        description = """
You attempt to eat the greaple when it suddenly becomes too bright to look at!

Luckily, as you have no eyes yet this doesn't affect you and you eat it anyways!

You now have the power to turn too bright to look at for short periods of time!

You have also evolved eyes!

Unfortunately, this means that you cannot use shine in packs otherwise you'll blind yourself!
"""

        choices = [("Evolve!", "evolveE"),
                   ("Split", "split")]

        super(EatGreaple, self).__init__("EatGreaple", description, choices)
