numElements :: [a] -> Int

numElements []     = 0
numElements (x:xs) = 1 + numElements xs
