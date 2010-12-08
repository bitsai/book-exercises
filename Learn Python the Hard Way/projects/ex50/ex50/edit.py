from page import *

class Edit(Page):
    def __init__(self):
        description = """
Choose what to give to your creatures!
"""

        choices = [("sharp teeth", "sharpTeeth")]

        super(Edit, self).__init__("Edit", description, choices)
