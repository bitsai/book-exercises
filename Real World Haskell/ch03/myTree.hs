data Tree a = Node a (Maybe (Tree a)) (Maybe (Tree a))
            deriving (Show)
