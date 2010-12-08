from page import *

class EatMaknarg(Page):
    def __init__(self):
        description = """
This Maknarg is bigger than any you have encountered before. The Maknarg catches your small platoon by surprise and eats one of your species!

You get a powerful blow on the Maknarg's side!

The Maknarg appears to fall dead.

You go over to eat it when suddenly it snaps one of your brethren in two.

You attempt finish off the Maknarg for good and eat his remains.
"""

        choices = [("eat Maknarg?", "eatMaknargQ")]

        super(EatMaknarg, self).__init__("EatMaknarg", description, choices)
