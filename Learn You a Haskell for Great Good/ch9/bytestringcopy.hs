import System.Environment
import qualified Data.ByteString.Lazy as B

main = do
  (fileName1:fileName2:_) <- getArgs
  copyFile fileName1 fileName2

copyFile :: FilePath -> FilePath -> IO ()
copyFile source dest = do
  contents <- B.readFile source
  B.writeFile dest contents
