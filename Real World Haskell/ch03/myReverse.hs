module MyReverse where

myReverse (x:xs) = myReverse xs ++ [x]
myReverse list   = list
