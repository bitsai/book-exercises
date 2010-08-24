import System.Environment (getArgs)

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
          myFunction = firstWords

firstWords :: String -> String
firstWords text = unlines (map firstWord (lines text))
    where firstWord line = head (words line)
