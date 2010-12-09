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
        choice = raw_input("> ")
        return self.choose(choice)

    def print_choices(self):
        idx = 0
        for description, nextRoom in self.choices:
            print "(", idx, ")", description
            idx += 1

    def choose(self, choice):
        if not choice.isdigit(): return self
        idx = int(choice)
        if idx < 0 or idx >= len(self.choices): return self
        description, nextRoom = self.choices[idx]
        return nextRoom
