import Data.Char
import Data.List

main = do
  line <- fmap (intersperse '-' . reverse . map toUpper) getLine
  putStrLn line

