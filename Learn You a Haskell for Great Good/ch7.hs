import Data.List
import Data.Char
import qualified Data.Map as Map
import qualified Data.Set as Set

numUniques :: (Eq a) => [a] -> Int
numUniques = length . nub

search :: (Eq a) => [a] -> [a] -> Bool
search needle haystack = 
  let len = length needle
      sublists = tails haystack
      f = (\acc x -> if take len x == needle then True else acc)
  in foldl f False sublists

encode :: Int -> String -> String
encode shift msg =
  let ords = map ord msg
      shifted = map (+ shift) ords
  in map chr shifted

decode :: Int -> String -> String
decode shift msg = encode (negate shift) msg

findKey :: (Eq k) => k -> [(k, v)] -> v
findKey key xs = snd . head . filter (\(k, v) -> key == k) $ xs

findKey' :: (Eq k) => k -> [(k, v)] -> Maybe v
findKey' _ [] = Nothing
findKey' key ((k, v):xs) = if key == k
                           then Just v
                           else findKey' key xs

findKey'' :: (Eq k) => k -> [(k, v)] -> Maybe v
findKey'' key xs = 
  let f = (\(k, v) acc -> if key == k then Just v else acc)
  in foldr f Nothing xs

fromList' :: (Ord k) => [(k, v)] -> Map.Map k v
fromList' xs = foldl (\acc (k, v) -> Map.insert k v acc) Map.empty xs

phoneBookToMap :: (Ord k) => [(k, String)] -> Map.Map k String
phoneBookToMap xs = Map.fromListWith (\num1 num2 -> num1 ++ ", " ++ num2) xs

phoneBookToMap' :: (Ord k) => [(k, v)] -> Map.Map k [v]
phoneBookToMap' xs = Map.fromListWith (++) $ map (\(k, v) -> (k, [v])) xs

text1 = "I just had an anime dream. Anime... Reality... Are they so different?"  
text2 = "The old man left his garbage can out and now his trash is all over my lawn!"

set1 = Set.fromList text1
set2 = Set.fromList text2
