from nose.tools import *
from ex47.room import *
from ex47.morpheusRoom import *
from ex47.princessRoom import *
from ex47.goldKoiRoom import *
from ex47.bearRoom import *
from ex47.gateRoom import *
from ex47.deathRoom import *

def test_room():
    gold = Room("GoldRoom",
                """This room has gold in it you can grab. There's a room to the north.""",
                "default result")
    assert_equal(gold.name, "GoldRoom")
    assert_equal(gold.actions, {})

def test_room_paths():
    center = Room("Center", "Test room in the center.", "default result")
    north = Room("North", "Test room in the north.", "default result")
    south = Room("South", "Test room in the south.", "default result")

    center.add_actions({'go north': ('went north!', north), 'go south': ('went south!', south)})
    assert_equal(center.do('go north'), north)
    assert_equal(center.do('go south'), south)

def test_map():
    room = MorpheusRoom()
    assert_equal(room.name, "MorpheusRoom")

    room = room.do('take red pill')
    assert_equal(room.name, "PrincessRoom")

    room = room.do('make her eat it')
    assert_equal(room.name, "GoldKoiRoom")

    room = room.do('throw it in')
    assert_equal(room.name, "BearRoom")

    room = room.do('say no')
    assert_equal(room.name, "GateRoom")

    room = room.do('open it')
    assert_equal(room.name, "DeathRoom")
