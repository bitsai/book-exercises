from sys import exit

def end(why):
    print why, "Good job!"
    exit(0)

def start():
    print "You are in The Matrix."
    print "Morpheus offers you a blue pill and a red pill."
    print "Which one do you take?"

    pill = raw_input("> ")

    if pill == "red":
        end("You start down the path to becoming The One.")
    else:
        end("You spend the rest of your life as a CopperTop.")

start()
