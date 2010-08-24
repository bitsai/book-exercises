import Data.Char
import Data.List
import Data.Maybe

-- p.84: #1
safeHead :: [a] -> Maybe a
safeHead []     = Nothing
safeHead (x:xs) = Just x

safeTail :: [a] -> Maybe [a]
safeTail []     = Nothing
safeTail (x:xs) = Just xs

safeLast :: [a] -> Maybe a
-- safeLast xs = safeHead (reverse xs)
safeLast []     = Nothing
safeLast [x]    = Just x
safeLast (x:xs) = safeLast xs

safeInit :: [a] -> Maybe [a]
safeInit []     = Nothing
safeInit [x]    = Just []
safeInit (x:xs) = Just (x:(fromJust (safeInit xs)))

-- p.84: #2
splitWith :: (a -> Bool) -> [a] -> [[a]]
splitWith pred [] = []
splitWith pred xs = filter (not . null) (token : tokens)
    where (token, rest) = break pred xs
          tokens = case rest of
                     (_:tail) -> splitWith pred tail
                     []       -> []

-- p.97: #1, #3
asInt_fold :: String -> Int
asInt_fold "" = error "empty string"
asInt_fold cs = foldl' step 0 cs
    where step acc c
              | isDigit c /= True = error ("not a digit '" ++ [c] ++ "'")
              | acc' < acc        = error "integer overflow"
              | otherwise         = acc'
              where acc' = (acc * 10) + (digitToInt c)

-- p.98, #4
type ErrorMessage = String
asInt_either :: String -> Either ErrorMessage Int
asInt_either "" = Left "empty string"
asInt_either cs = foldl' step (Right 0) cs
    where step (Right acc) c
              | isDigit c /= True = Left ("not a digit '" ++ [c] ++ "'")
              | acc' < acc        = Left "integer overflow"
              | otherwise         = Right acc'
              where acc' = (acc * 10) + (digitToInt c)
          step (Left e) _ = Left e

-- p.98: #6
concat_foldr :: [[a]] -> [a]
concat_foldr xs = foldr (++) [] xs

-- p.98: #7
takeWhile_rec :: (a -> Bool) -> [a] -> [a]
takeWhile_rec _ [] = []
takeWhile_rec pred (x:xs) | pred x    = x : (takeWhile_rec pred xs)
                          | otherwise = []

takeWhile_foldr :: (a -> Bool) -> [a] -> [a]
takeWhile_foldr pred xs = foldr step [] xs
    where step x acc | pred x    = x : acc
                     | otherwise = []

-- p.98: #9
groupBy_fold :: (a -> a -> Bool) -> [a] -> [[a]]
groupBy_fold equal xs = foldl' step [] xs
    where step [] x = [[x]]
          step acc x 
              | equal x (head (last acc)) = (init acc) ++ [(last acc) ++ [x]]
              | otherwise = acc ++ [[x]]

-- p.98: #10
myAny_foldl :: (a -> Bool) -> [a] -> Bool
myAny_foldl pred xs = foldl' step False xs
    where step acc x = acc || (pred x)

myAny_foldr :: (a -> Bool) -> [a] -> Bool
myAny_foldr pred xs = foldr step False xs
    where step x acc = (pred x) || acc

myCycle_foldr :: [a] -> [a]
myCycle_foldr xs = foldr (:) (myCycle_foldr xs) xs

myWords_foldl :: String -> [String]
myWords_foldl cs = filter (not . null) (foldl' step [] cs)
    where step [] c | isSpace c = []
                    | otherwise = [[c]]
          step ws c | isSpace c = ws ++ [""]
                    | otherwise = (init ws) ++ [(last ws) ++ [c]]

myWords_foldr :: String -> [String]
myWords_foldr cs = filter (not . null) (foldr step [] cs)
    where step c [] | isSpace c = []
                    | otherwise = [[c]]
          step c (w:ws) | isSpace c = "" : (w : ws)
                        | otherwise = (c : w) : ws

myUnlines_foldl :: [String] -> String
myUnlines_foldl ls = foldl' step [] ls
    where step acc l = acc ++ l ++ "\n"

myUnlines_foldr :: [String] -> String
myUnlines_foldr ls = foldr step [] ls
    where step l acc = l ++ "\n" ++ acc
