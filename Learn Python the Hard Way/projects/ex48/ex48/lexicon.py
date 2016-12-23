directions = ('north', 'south', 'east', 'west', 'down', 'up', 'left', 'right', 'back')
verbs = ('go', 'stop', 'kill', 'eat', 'open')
stop_words = ('the', 'in', 'of', 'from', 'at', 'it')
nouns = ('door', 'bear', 'princess', 'cabinet')

def get_tuple(word):
    test_word = word

    if test_word in directions:
        return ('direction', word.lower())
    elif test_word in verbs:
        return ('verb', word.lower())
    elif test_word in stop_words:
        return ('stop', word.lower())
    elif test_word in nouns:
        return ('noun', word.lower())
    elif test_word.isdigit():
        return ('number', int(word))
    else:
        return ('error', word)

def scan(sentence):
    words = sentence.split()
    return [get_tuple(word) for word in words]
