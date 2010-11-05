name = 'Benny Tsai'
age = 29
height_in = 70 # inches
height_cm = height_in * 2.54 # centimeters
weight_lb = 158 # pounds
weight_kg = weight_lb * 0.4536 # kilograms
eyes = 'Brown'
teeth = 'Yellow'
hair = 'Black'

print "Let's talk about %r." % name
print "He's %r inches, %r centimeters tall." % (height_in, height_cm)
print "He's %r pounds, %r kilograms heavy." % (weight_lb, weight_kg)
print "Actually that's not too heavy."
print "He's got %r eyes and %r hair." % (eyes, hair)
print "His teeth are usually %r depending on brushing frequency." % teeth

# this line is tricky, try to get it exactly right
print "If I add %r, %r, and %r I get %r." % (
    age, height_in, weight_lb, age + height_in + weight_lb)
