class Engine(object):
    def play(self, startRoom):
        room = startRoom

        while True:
            print "\n--------"
            room = room.enter()
