class Room(object):
    def __init__(self, name, choices):
        self.name = name
        self.choices = choices
        filepath = "../descriptions/" + name + ".txt"
        with open(filepath) as f:
            self.description = f.read()

    def enter(self):
        print self.description
        self.print_choices()
        selection = raw_input("\n> ")
        return self.choose(selection)

    def print_choices(self):
        idx = 0
        for choice, consequence, nextRoom in self.choices:
            print "(", idx, ")", choice
            idx += 1

    def choose(self, selection):
        if not selection.isdigit(): return self
        idx = int(selection)
        if idx < 0 or idx >= len(self.choices): return self
        choice, consequence, nextRoom = self.choices[idx]
        print "\n", consequence
        return nextRoom
