cities = {'CA': 'San Francisco',
          'MI': 'Detroit',
          'FL': 'Jacksonville'}

cities['NY'] = 'New York'
cities['OR'] = 'Portland'

def find_city(the_map, state):
    if state in the_map:
        return the_map[state]
    else:
        return "Not found."

# ok pay attention!
cities['_find'] = find_city

while True:
    print "State? (ENTER to quit)",
    state = raw_input("> ")

    if not state: break

    # this line is the most important ever! study!
    city_found = cities['_find'](cities, state)
    print city_found

for k, v in cities.items():
    print k, v
