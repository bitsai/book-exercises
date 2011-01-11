import System.Environment
import System.Directory
import System.IO
import Data.List

dispatch :: [(String, [String] -> IO ())]
dispatch = [ ("add", add)
           , ("view", view)
           , ("remove", remove)
           , ("bump", bump)
           ]

main = do
  (command:args) <- getArgs
  let (Just action) = lookup command dispatch
  action args

add :: [String] -> IO ()
add [fileName, todoItem] = appendFile fileName (todoItem ++ "\n")

view :: [String] -> IO ()
view [fileName] = do
  contents <- readFile fileName
  let tasks = lines contents
      numberedTasks = zipWith (\n line -> show n ++ " - " ++ line) [0..] tasks
  putStr $ unlines numberedTasks

remove :: [String] -> IO ()
remove [fileName, numberString] = do
  (tempName, tempHandle) <- openTempFile "." "temp"
  contents <- readFile fileName
  let number = read numberString
      tasks = lines contents
      newTasks = delete (tasks !! number) tasks
  hPutStr tempHandle $ unlines newTasks
  hClose tempHandle
  removeFile fileName
  renameFile tempName fileName

bump :: [String] -> IO ()
bump [fileName, numberString] = do
  (tempName, tempHandle) <- openTempFile "." "temp"
  contents <- readFile fileName
  let number = read numberString
      tasks = lines contents
      bumpee = tasks !! number
      newTasks = bumpee : delete bumpee tasks
  hPutStr tempHandle $ unlines newTasks
  hClose tempHandle
  removeFile fileName
  renameFile tempName fileName
