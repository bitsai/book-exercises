from evolution import *
from start import *
from nourish import *
from maknarg import *
from elgeast import *
from begndusk import *
from eatMaknargQ import *
from eatElgeastQ import *
from eatGreapleQ import *
from eatRandar import *
from eatGreaple import *
from eatElgeast import *
from split import *
from eatMaknarg import *
from eatSlizeox import *
from evolveE import *
from edit import *
from sharpTeeth import *
from useShine import *
from useBite import *
from bite import *
from biteE import *
from finalFight import *
from biteAwayE import *
from biteEE import *
from shineE import *

pages = {"evolution": Evolution(),
         "start": Start(),
         "nourish": Nourish(),
         "maknarg": Maknarg(),
         "elgeast": Elgeast(),
         "begndusk": Begndusk(),
         "eatMaknargQ": EatMaknargQ(),
         "eatElgeastQ": EatElgeastQ(),
         "eatGreapleQ": EatGreapleQ(),
         "eatRandar": EatRandar(),
         "eatGreaple": EatGreaple(),
         "eatElgeast": EatElgeast(),
         "split": Split(),
         "eatMaknarg": EatMaknarg(),
         "eatSlizeox": EatSlizeox(),
         "evolveE": EvolveE(),
         "edit": Edit(),
         "sharpTeeth": SharpTeeth(),
         "useShine": UseShine(),
         "useBite": UseBite(),
         "bite": Bite(),
         "biteE": BiteE(),
         "finalFight": FinalFight(),
         "biteAwayE": BiteAwayE(),
         "biteEE": BiteEE(),
         "shineE": ShineE()}

class Engine(object):
    def goto(self, pageName):
        print "\n--------"
        page = pages.get(pageName)
        nextPageName = page.read()
        self.goto(nextPageName)

e = Engine()
e.goto("evolution")
