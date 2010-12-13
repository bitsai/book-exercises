class Room(object):
    def __init__(self, name, choices, next_room):
        self.name = name
        self.choices = choices
        self.next_room = next_room
        filepath = "descriptions/" + name + ".txt"
        with open(filepath) as f:
            self.description = f.read()

    def enter(self, score):
        print self.description
        self.print_choices()
        selection = raw_input("\n> ")
        return self.choose(selection, score)

    def print_choices(self):
        idx = 0
        for choice, _, _ in self.choices:
            print "(", idx, ")", choice
            idx += 1

    def choose(self, selection, score):
        if not selection.isdigit(): return self, score
        idx = int(selection)
        if idx not in range(len(self.choices)): return self, score
        _, result, points = self.choices[idx]
        print "\n", result
        return self.next_room, score + points
