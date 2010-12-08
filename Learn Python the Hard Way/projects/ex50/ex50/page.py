class Page(object):
    def __init__(self, name, description, choices):
        self.name = name
        self.description = description
        self.choices = choices

    def read(self):
        print self.description
        self.print_choices()
        choice = raw_input("> ")
        return self.choose(choice)

    def print_choices(self):
        idx = 0

        for description, nextPage in self.choices:
            print "(", idx, ")", description
            idx += 1

    def choose(self, choice):
        if not choice.isdigit(): return self
        idx = int(choice)
        if idx < 0 or idx >= len(self.choices): return self
        description, nextPage = self.choices[idx]
        return nextPage
