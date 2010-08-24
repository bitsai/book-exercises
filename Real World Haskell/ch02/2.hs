--lastButOne list = if length list < 2
--                  then error "list too short"
--                  else head (drop (length list - 2) list)

lastButOne list = if length list < 2
                  then Nothing
                  else Just (head (drop (length list - 2) list))
