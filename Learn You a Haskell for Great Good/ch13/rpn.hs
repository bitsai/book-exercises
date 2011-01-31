import Data.List
import Control.Monad

solveRPN :: String -> Maybe Double
solveRPN s = do
  [result] <- foldM foldingFunction [] (words s)
  return result

foldingFunction :: [Double] -> String -> Maybe [Double]
foldingFunction (x:y:ys) "*" = return ((x * y):ys)
foldingFunction (x:y:ys) "+" = return ((x + y):ys)
foldingFunction (x:y:ys) "-" = return ((x - y):ys)
foldingFunction xs numStr = liftM (:xs) (readMaybe numStr)

readMaybe :: (Read a) => String -> Maybe a
readMaybe s = case reads s of [(x, "")] -> Just x
                              _ -> Nothing
