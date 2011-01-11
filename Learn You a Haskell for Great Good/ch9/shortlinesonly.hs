main = interact $ unlines . filter ((< 10) . length) . lines
