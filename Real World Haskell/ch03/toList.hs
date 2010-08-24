data List a = Cons a (List a)
            | Nil
              deriving (Show)

fromList []     = Nil
fromList (x:xs) = Cons x (fromList xs)

toList Nil         = []
toList (Cons x xs) = x:(toList xs)
