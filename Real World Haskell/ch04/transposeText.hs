import System.Environment (getArgs)

import Data.List

interactWith function inputFile outputFile = do
  input <- readFile inputFile
  writeFile outputFile (function input)

main = mainWith myFunction
    where mainWith function = do
            args <- getArgs
            case args of
              [input, output] -> interactWith function input output
              _               -> putStrLn "two arguments needed"
            -- replace "id" with name of function
          myFunction = transposeText

transposeText :: String -> String
transposeText text = unlines (transpose (lines text))
