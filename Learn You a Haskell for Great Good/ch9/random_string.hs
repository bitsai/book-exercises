import System.Random
import Data.List

main = do
  gen <- getStdGen
  putStrLn $ take 20 (randomRs ('a', 'z') gen)
  gen' <- newStdGen
  putStr $ take 20 (randomRs ('a', 'z') gen')
