class Room(object):
    def __init__(self, name, description, default_result):
        self.name = name
        self.description = description
        self.default_result = default_result
        self.actions = {}

    def add_actions(self, actions):
        self.actions.update(actions)

    def enter(self):
        print self.description
        action = raw_input("> ")
        return self.do(action)

    def do(self, action):
        result, room = self.actions.get(action, (self.default_result, self))
        print result
        return room
