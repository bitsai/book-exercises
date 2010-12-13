class End(object):
    def enter(self, score):
        print "You got", score, "correct"

        if score in range(4):
            print """
'Ho ho ho, very good!' booms Jabba.  He frees you from your chains, and defrosts Han Solo... only to have you both tossed into the Great Pit of Carkoon, where you find a new definition of pain and suffering as you are slowly digested over a thousand years.
"""
        elif score in range(5, 10):
            print """
'Ho ho ho, very good... but not good enough!' booms Jabba.  Han remains frozen in carbonite, and you remain trapped in chains (and a very tasteful gold bikini).  You spend your days dancing for Jabba's pleasure, and your nights plotting your escape...
"""
        else:
            print """
'Ho ho ho, very good!' booms Jabba.  He frees you from your chains, and defrosts Han Solo... only to order you both tossed into the Great Pit of Carkoon, where you will find a new definition of pain and suffering as you are slowly digested over a thousand years.

But just in the nick of time, Lando and Luke show up on the scene!  With your powers combined, you rescue Han, escape from Jabba's clutches, and you personally put an end to the loathsome creature's criminal career.  Reunited, the gang zooms away to take on the Emperor, Darth Vader, and the forces of the Empire...
"""

        exit()
