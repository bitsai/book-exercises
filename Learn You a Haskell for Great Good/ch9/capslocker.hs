import Data.Char  
  
main = do
  contents <- getContents
  putStr (map toUpper contents)
