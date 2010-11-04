# 100 cars
cars = 100

# 4 spaces per car
space_in_a_car = 4.0

# 30 drivers
drivers = 30

# 90 passengers
passengers = 90

# 100 cars - 30 drivers = 70 cars not driven
cars_not_driven = cars - drivers

# 30 drivers = 30 cars driven
cars_driven = drivers

# 30 cars driven * 4 spaces per car = 120 spaces
carpool_capacity = cars_driven * space_in_a_car

# 90 passengers / 30 cars driven = 3 passengers per car
average_passengers_per_car = passengers / cars_driven

print "There are", cars, "cars available."
print "There are only", drivers, "drivers available."
print "There will be", cars_not_driven, "empty cars today."
print "We can transport", carpool_capacity, "people today."
print "We have", passengers, "to carpool today."
print "We need to put about", average_passengers_per_car, "in each car."

x = 1
y = 2
print x + y
