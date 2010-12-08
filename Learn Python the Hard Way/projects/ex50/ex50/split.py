from page import *

class Split(Page):
    def __init__(self):
        description = """
You split into 3 smaller beings.
"""

        choices = [("eat Maknarg", "eatMaknarg"),
                   ("eat Greaple", "eatGreaple"),
                   ("eat Slizeox", "eatSlizeox")]

        super(Split, self).__init__("Split", description, choices)
