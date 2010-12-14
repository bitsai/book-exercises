from nose.tools import *
from ex50.start import *

def test_room():
    s = Start()
    assert_equal(s.name, "Start")
    assert_equal(s.next_room.name, "Q1")
    assert_equal(len(s.choices), 1)
    assert_equal(s.choices[0], ("Continue", "You begin the test...", 0))

def test_choices():
    s = Start()
    next_room, new_score = s.choose("", 0)
    assert_equal(next_room.name, "Start")
    assert_equal(new_score, 0)
    next_room, new_score = s.choose("asdf", 0)
    assert_equal(next_room.name, "Start")
    assert_equal(new_score, 0)
    next_room, new_score = s.choose("1", 0)
    assert_equal(next_room.name, "Start")
    assert_equal(new_score, 0)
    next_room, new_score = s.choose("0", 0)
    assert_equal(next_room.name, "Q1")
    assert_equal(new_score, 0)

def test_game():
    room = Start()
    assert_equal(room.name, "Start")
    room = room.next_room
    assert_equal(room.name, "Q1")
    room = room.next_room
    assert_equal(room.name, "Q2")
    room = room.next_room
    assert_equal(room.name, "Q3")
    room = room.next_room
    assert_equal(room.name, "Q4")
    room = room.next_room
    assert_equal(room.name, "Q5")
    room = room.next_room
    assert_equal(room.name, "Q6")
    room = room.next_room
    assert_equal(room.name, "Q7")
    room = room.next_room
    assert_equal(room.name, "Q8")
    room = room.next_room
    assert_equal(room.name, "Q9")
    room = room.next_room
    assert_equal(room.name, "Q10")
    room = room.next_room
    assert_equal(room, None)
