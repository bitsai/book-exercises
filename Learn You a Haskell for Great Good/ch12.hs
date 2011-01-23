import Control.Monad

applyMaybe :: Maybe a -> (a -> Maybe b) -> Maybe b
applyMaybe Nothing f = Nothing
applyMaybe (Just x) f = f x

type Birds = Int
type Pole = (Birds, Birds)

landLeft :: Birds -> Pole -> Maybe Pole
landLeft n (left, right) 
  | abs ((left + n) - right) < 4 = Just (left + n, right)
  | otherwise = Nothing

landRight :: Birds -> Pole -> Maybe Pole
landRight n (left, right) 
  | abs (left - (right + n)) < 4 = Just (left, right + n)
  | otherwise = Nothing

x -: f = f x

banana :: Pole -> Maybe Pole
banana _ = Nothing

foo :: Maybe String
foo = do
  x <- Just 3
  y <- Just "!"
  Just (show x ++ y)

marySue :: Maybe Bool
marySue = do
  x <- Just 9
  Just (x > 8)

routine :: Maybe Pole
routine = do
  start <- return (0, 0)
  first <- landLeft 2 start
  Nothing
  second <- landRight 2 first
  landLeft 1 second

wopwop :: Maybe Char
wopwop = do
  (x:xs) <- Just ""
  return x

listOfTuples :: [(Int, Char)]
listOfTuples = do
  n <- [1, 2]
  ch <- ['a', 'b']
  return (n, ch)

sevensOnly :: [Int]
sevensOnly = do
  x <- [1..50]
  guard ('7' `elem` show x)
  return x

type KnightPos = (Int, Int)

moveKnight :: KnightPos -> [KnightPos]
moveKnight (c, r) = do
  (c', r') <- [(c + 2, r - 1), (c + 2, r + 1), (c - 2, r - 1), 
               (c - 2, r + 1), (c + 1, r - 2), (c + 1, r + 2), 
               (c - 1, r - 2), (c - 1, r + 2)]
  guard (c' `elem` [1..8] && r' `elem` [1..8])
  return (c', r')

in3 :: KnightPos -> [KnightPos]
in3 start = do
  first <- moveKnight start
  second <- moveKnight first
  moveKnight second

canReachIn3 :: KnightPos -> KnightPos -> Bool
canReachIn3 start end = end `elem` in3 start

validPos :: KnightPos -> Bool
validPos (c, r) = c `elem` [1..8] && r `elem` [1..8]

nextMoves :: KnightPos -> [KnightPos]
nextMoves (c, r) = 
  filter validPos [(c + 2, r - 1), (c + 2, r + 1), (c - 2, r - 1), 
                   (c - 2, r + 1), (c + 1, r - 2), (c + 1, r + 2), 
                   (c - 1, r - 2), (c - 1, r + 2)]

moveKnight' :: [KnightPos] -> [[KnightPos]]
moveKnight' moves = 
  [moves ++ [nextMove] | nextMove <- nextMoves (last moves)]

in3' :: [KnightPos] -> [[KnightPos]]
in3' start = return start >>= moveKnight' >>= moveKnight' >>= moveKnight'

canReachIn3' :: KnightPos -> KnightPos -> [[KnightPos]]
canReachIn3' start end = 
  let allMoves = in3' [start] 
      reachesEnd moves = (last moves) == end in
    filter reachesEnd allMoves
