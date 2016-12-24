directions = ('north', 'south', 'east', 'west', 'down', 'up', 'left', 'right', 'back')
verbs = ('go', 'stop', 'kill', 'eat', 'open')
stop_words = ('the', 'in', 'of', 'from', 'at', 'it')
nouns = ('door', 'bear', 'princess', 'cabinet')

def get_tuple(word):
    lowercased = word.lower()

    if lowercased in directions:
        return ('direction', lowercased)
    elif lowercased in verbs:
        return ('verb', lowercased)
    elif lowercased in stop_words:
        return ('stop', lowercased)
    elif lowercased in nouns:
        return ('noun', lowercased)
    elif lowercased.isdigit():
        return ('number', int(lowercased))
    else:
        # in case of error, return word with original casing
        return ('error', word)

def scan(sentence):
    words = sentence.split()
    return [get_tuple(word) for word in words]
