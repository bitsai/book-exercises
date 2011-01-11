import Data.List

solveRPN :: String -> Float
solveRPN = head . foldl func [] . words
  where func (x:y:ys) "*" = (x * y):ys
        func (x:y:ys) "+" = (x + y):ys
        func (x:y:ys) "-" = (y - x):ys
        func (x:y:ys) "/" = (y / x):ys
        func (x:y:ys) "^" = (y ** x):ys
        func (x:xs) "ln" = log x:xs
        func xs "sum" = [sum xs]
        func xs numberString = read numberString:xs
