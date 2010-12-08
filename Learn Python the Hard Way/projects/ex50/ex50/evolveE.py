from page import *

class EvolveE(Page):
    def __init__(self):
        description = """
Millions of years pass. The tiny cell you started as is no more. It has evolved, mutated, grown and changed. It has came ashore and has truly begun the quest for survival!
"""

        choices = [("edit your creature!", "edit")]

        super(EvolveE, self).__init__("EvolveE", description, choices)
