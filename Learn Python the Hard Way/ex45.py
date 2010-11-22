## Animal is-a object (yes, sort of confusing) look at the extra credit
class Animal(object):
    def feed(self):
        print "nom nom nom!"

## Dog is-a Animal
class Dog(Animal):
    def __init__(self, name):
        ## Dog has-a name
        self.name = name

## Cat is-a Animal
class Cat(Animal):
    def __init__(self, name):
        ## Cat has-a name
        self.name = name

    def feed(self):
        print "meow!"

## Person is-a object
class Person(object):
    def __init__(self, name):
        ## Person has-a name
        self.name = name

        ## Person has-a pet of some kind
        self.pet = None

        ## Person has-many hobbies
        self.hobbies = ()

## Employee is-a Person
class Employee(Person):
    def __init__(self, name, salary):
        ## call __init__ method of Employee's super-class (Person)
        super(Employee, self).__init__(name)

        ## Employee has-a salary
        self.salary = salary

## Fish is-a object
class Fish(object):
    pass

## Salmon is-a Fish
class Salmon(Fish):
    pass

## Halibut is-a Fish
class Halibut(Fish):
    pass

## rover is-a Dog
rover = Dog("Rover")

## satan is-a Cat
satan = Cat("Satan")

## mary is-a person
mary = Person("Mary")

## mary has-a pet, satan
mary.pet = satan

## frank is-a Employee
frank = Employee("Frank", 120000)

## frank has-a pet, rover
frank.pet = rover

## flipper is-a Fish
flipper = Fish()

## crouse is-a Salmon
crouse = Salmon()

## harry is-a Halibut
harry = Halibut()

rover.feed()
satan.feed()

mary.hobbies = ('have pet lamb', 'go to school', 'love pet lamb')
print mary.hobbies
