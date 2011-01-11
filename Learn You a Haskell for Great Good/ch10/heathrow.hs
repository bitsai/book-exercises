import Data.List
  
data Section = Section { getA :: Int, getB :: Int, getC :: Int } deriving (Show)
type RoadSystem = [Section]

heathrowToLondon :: RoadSystem
heathrowToLondon = [Section 50 10 30, 
                    Section 5 90 20, 
                    Section 40 2 25,
                    Section 10 8 0]

data Label = A | B | C deriving (Show)
type Path = [(Label, Int)]

roadStep :: (Path, Path) -> Section -> (Path, Path)
roadStep (pathA, pathB) (Section a b c) =
  let costA = sum $ map snd pathA
      costB = sum $ map snd pathB
      forwardCostToA = costA + a
      crossCostToA = costB + b + c
      forwardCostToB = costB + b
      crossCostToB = costA + a + c
      newPathToA = if forwardCostToA <= crossCostToA
                   then (A, a):pathA
                   else (C, c):(B, b):pathB
      newPathToB = if forwardCostToB <= crossCostToB
                   then (B, b):pathB
                   else (C, c):(A, a):pathA
  in (newPathToA, newPathToB)

optimalPath :: RoadSystem -> Path
optimalPath roadSystem =
  let (bestAPath, bestBPath) = foldl roadStep ([], []) roadSystem
  in if sum (map snd bestAPath) <= sum (map snd bestBPath)
     then reverse bestAPath
     else reverse bestBPath

groupsOf :: Int -> [a] -> [[a]]
groupsOf 0 _ = undefined
groupsOf _ [] = []
groupsOf n xs = take n xs : groupsOf n (drop n xs)

main = do
  contents <- getContents
  let triples = groupsOf 3 (map read $ lines contents)
      roadSystem = map (\[a, b, c] -> Section a b c) triples
      path = optimalPath roadSystem
      pathString = concat $ map (show . fst) path
      pathPrice = sum $ map snd path
  putStrLn $ "The best path to take is: " ++ pathString
  putStrLn $ "The best price is: " ++ show pathPrice
