data Tree a = Node a (Tree a) (Tree a)
            | Empty
              deriving (Show)

height Empty               = 0
height (Node _ left right) = 1 + max (height left) (height right)
