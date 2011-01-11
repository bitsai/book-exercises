import System.Environment
import System.IO
import System.IO.Error

main = toTry `catch` handler

toTry :: IO ()
toTry = do 
  (fileName:_) <- getArgs
  contents <- readFile fileName      
  putStrLn $ "The file has " ++ show (length (lines contents)) ++ " lines!"

handler :: IOError -> IO ()
handler e
  | isDoesNotExistError e =
    case ioeGetFileName e of 
      Just path -> putStrLn $ "File does not exist at: " ++ path
      Nothing -> putStrLn $ "File does not exist, unknown location"
  | otherwise = ioError e
