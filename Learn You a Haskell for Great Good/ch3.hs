removeNonUppercase :: String -> String
removeNonUppercase s = [c | c <- s, elem c ['A'..'Z']]

addThree:: Int -> Int -> Int -> Int
addThree x y z = x + y + z

factorial :: Integer -> Integer
factorial n = product [1..n]

circumference :: Float -> Float
circumference r = 2 * pi * r

circumference' :: Double -> Double
circumference' r = 2 * pi * r
