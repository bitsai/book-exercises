data Color = Red | Green | Blue

instance Show Color where
  show Red   = "Red"
  show Green = "Green"
  show Blue  = "Blue"

trim (c:cs) =
  case c of
    ' ' -> trim cs
    _   -> c:cs

instance Read Color where
    readsPrec _ value =
        tryParse [("Red", Red), ("Green", Green), ("Blue", Blue)]
        where trimmedVal = trim(value)
              tryParse [] = []
              tryParse ((attempt, result):xs) =
                      if (take (length attempt) trimmedVal) == attempt
                         then [(result, drop (length attempt) trimmedVal)]
                         else tryParse xs
