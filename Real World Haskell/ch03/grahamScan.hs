-- http://books.google.com/books?id=NLngYyWFl_YC&lpg=PA949&pg=PA949

import Data.List
import Direction
import GetTurn

dist (x1, y1) (x2, y2) = sqrt (a * a + b * b)
    where a = abs (x2 - x1); b = abs (y2 - y1)

compareYX (x1, y1) (x2, y2)
    | y1 /= y2  = compare y1 y2
    | otherwise = compare x1 x2

-- Calculates cotangent of angle between line p1-p2 and x-axis
-- Simple because p1 always has lower y in our program
cot (x1, y1) (x2, y2) = adj / opp
    where adj = (x2 - x1); opp = (y2 - y1)

compareCot p0 p1 p2 = flip compare (cot p0 p1) (cot p0 p2)

-- Given p0 and list of points sorted by cot(p0, pi),
-- finds points with the same cot and keeps only the one furthest from p0
removeDupCot _ []            = []
removeDupCot _ [p]           = [p]
removeDupCot p0 (p1:(p2:ps)) = if (cot p0 p1) == (cot p0 p2)
                               then if (dist p0 p1) > (dist p0 p2)
                                    then removeDupCot p0 (p1:ps)
                                    else removeDupCot p0 (p2:ps)
                               else p1:(removeDupCot p0 (p2:ps))

findHull hull [] = hull
findHull hull (p:ps)
    | getTurn a b p /= LeftTurn = findHull hullMinusLast (p:ps)
    | otherwise                 = findHull (hull++[p]) ps
    where hullMinusLast = take (length hull - 1) hull
          a = last hullMinusLast
          b = last hull

grahamScan points = findHull (take 3 ordered) (drop 3 ordered)
    where sortedByYX = sortBy compareYX points
          minYX = head sortedByYX
          restByCot = sortBy (compareCot minYX) (tail sortedByYX)
          ordered = minYX:(removeDupCot minYX restByCot)

p1 = ((-1.0), 2.0)
p2 = (0.0, 2.0)
p3 = (1.0, 2.0)
p4 = (2.0, 2.0)
p5 = ((-1.0), 1.0)
p6 = (0.0, 1.0)
p7 = (1.0, 1.0)
p8 = (2.0, 1.0)
p9 = ((-1.0), 0.0)
p10 = (0.0, 0.0)
p11 = (1.0, 0.0)
p12 = (2.0, 0.0)
p13 = ((-1.0), (-1.0))
p14 = (0.0, (-1.0))
p15 = (1.0, (-1.0))
p16 = (2.0, (-1.0))

test1 = [p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16]
test2 = [p1, p2, p3, p6, p10, p14]
test3 = [p1, p2, p3, p4, p5, p6, p7, p9, p10, p13]
test4 = [p1, p2, p3, p4, p6, p7, p8, p11, p12, p16]
