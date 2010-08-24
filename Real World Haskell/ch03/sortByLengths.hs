import Data.List

compareLengths a b
    | length a > length b = GT
    | length a < length b = LT
    | otherwise           = EQ

sortByLengths list = sortBy compareLengths list
