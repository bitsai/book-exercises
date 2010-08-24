-- http://en.wikipedia.org/wiki/Graham_scan

module GetTurn where

import Direction

getTurn a b c
    | cross a b c > 0 = LeftTurn
    | cross a b c < 0 = RightTurn
    | otherwise       = Straight
    where cross (x1, y1) (x2, y2) (x3, y3) =
              (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)
