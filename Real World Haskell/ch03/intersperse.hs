intersperse :: a -> [[a]] -> [a]

intersperse separator []     = []
intersperse separator [x]    = x
intersperse separator (x:xs) = x ++ [separator] ++ intersperse separator xs
