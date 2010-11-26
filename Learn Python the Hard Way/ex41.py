from sys import exit
from random import randint

def death(action):
    """"""

    quips = ["You died. You kinda suck at this.",
             "Your mom would be proud. If she were smarter.",
             "Such a luser.",
             "I have a small puppy that's better at this."]

    rand_quip_idx = randint(0, len(quips) - 1)
    print quips[rand_quip_idx]
    exit(1)

def morpheus(action):
    """
    Morpheus is seated before you.
    He offers you two pills, a blue and a red.
    """

    if action == "take red pill":
        print "Bizarreness ensues, and darkness descends..."
        return 'princess_lives_here'

    elif action == "take blue pill":
        print "You go on to lead a miserable, boring life."
        print "And die a miserable, boring death."
        return 'death'

    else:
        print "Morpheus just stares at you from behind his awesome shades."
        return 'morpheus'

def princess_lives_here(action):
    """
    You see a beautiful Princess with a shiny crown.
    She offers you some cake.
    """

    if action == "eat it":
        print "You explode like a pinata full of frogs."
        print "The Princess cackles and eats the frogs. Yum!"
        return 'death'

    elif action == "do not eat it":
        print "She throws the cake at you and it cuts off your head."
        print "The last thing you see is her munching on your torso. Yum!"
        return 'death'

    elif action == "make her eat it":
        print "The Princess screams as you cram the cake in her mouth."
        print "Then she smiles and cries and thanks you for saving her."
        print "She points to a tiny door and says, 'The Koi needs cake too.'"
        print "She gives you the very last bit of cake and shoves you in."
        return 'gold_koi_pond'

    else:
        print "The princess looks at you confused and just points a the cake."
        return 'princess_lives_here'

def gold_koi_pond(action):
    """
    There is a garden with a koi pond in the center.
    You walk close and see a massive fin poke out.
    You peek in and a creepy looking huge Koi stares at you.
    It opens its mouth waiting for food.
    """

    if action == "feed it":
        print "The Koi jumps up, and rather than eating the cake, eats your arm."
        print "You fall in and the Koi shrugs then eats you."
        print "You are then pooped out sometime later."
        return 'death'

    elif action == "do not feed it":
        print "The Koi grimaces, then thrashes around for a second."
        print "It rushes to the other end of the pond, braces against the wall..."
        print "then it *lunges* out of the water, up in the air and over your"
        print "entire body, cake and all."
        print "You are then pooped out a week later."
        return 'death'

    elif action == "throw it in":
        print "The Koi wiggles, then leaps into the air to eat the cake."
        print "You can see it's happy, it then grunts, thrashes..."
        print "and finally rolls over and poops a magic diamond into the air"
        print "at your feet."
        return 'bear_with_sword'

    else:
        print "The Koi gets annoyed and wiggles a bit."
        return 'gold_koi_pond'

def bear_with_sword(action):
    """
    Puzzled, you are about to pick up the fish poop diamond when
    a bear bearing a load bearing sword walks in.
    Hey! That\'s my diamond! Where\'d you get that!?"
    It holds its paw out and looks at you.
    """

    if action == "give it":
        print "The bear swipes at your hand to grab the diamond and"
        print "rips your hand off in the process. It then looks at"
        print 'your bloody stump and says, "Oh crap, sorry about that."'
        print "It tries to put your hand back on, but you collapse."
        print "The last thing you see is the bear shrug and eat you."
        return 'death'

    elif action == "say no":
        print "The bear looks shocked. Nobody ever told a bear"
        print "with a broadsword 'no'. It asks,"
        print '"Is it because it\'s not a katana? I could go get one!"'
        print "It then runs off and now you notice a big iron gate."
        print '"Where the hell did that come from?" You say.'
        return 'big_iron_gate'

    else:
        print "The bear looks puzzled as to why you'd do that."
        return "bear_with_sword"

def big_iron_gate(action):
    """
    You walk up to the big iron gate and see there's a handle.
    """

    if action == 'open it':
        print "You open it and you are free!"
        print "There are mountains. And berries! And..."
        print "Oh, but then the bear comes with his katana and stabs you."
        print '"Who\'s laughing now!? Love this katana."'
        return 'death'

    else:
        print "That doesn't seem sensible. I mean, the door's right there."
        return 'big_iron_gate'

ROOMS = {
    'death': death,
    'morpheus': morpheus,
    'princess_lives_here': princess_lives_here,
    'gold_koi_pond': gold_koi_pond,
    'big_iron_gate': big_iron_gate,
    'bear_with_sword': bear_with_sword
}

def runner(map, start):
    next = start

    while True:
        room = map[next]
        print room.__doc__
       
        if next != 'death':
            action = raw_input("> ")
            print ""
 
        next = room(action)

runner(ROOMS, 'morpheus')
