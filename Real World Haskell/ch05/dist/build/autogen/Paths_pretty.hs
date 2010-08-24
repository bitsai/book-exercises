module Paths_pretty (
    version,
    getBinDir, getLibDir, getDataDir, getLibexecDir,
    getDataFileName
  ) where

import Data.Version (Version(..))
import System.Environment (getEnv)

version :: Version
version = Version {versionBranch = [0,1], versionTags = []}

bindir, libdir, datadir, libexecdir :: FilePath

bindir     = "/usr/local/bin"
libdir     = "/usr/local/lib/pretty-0.1/ghc-6.10.4"
datadir    = "/usr/local/share/pretty-0.1"
libexecdir = "/usr/local/libexec"

getBinDir, getLibDir, getDataDir, getLibexecDir :: IO FilePath
getBinDir = catch (getEnv "pretty_bindir") (\_ -> return bindir)
getLibDir = catch (getEnv "pretty_libdir") (\_ -> return libdir)
getDataDir = catch (getEnv "pretty_datadir") (\_ -> return datadir)
getLibexecDir = catch (getEnv "pretty_libexecdir") (\_ -> return libexecdir)

getDataFileName :: FilePath -> IO FilePath
getDataFileName name = do
  dir <- getDataDir
  return (dir ++ "/" ++ name)
