module Geometry.Sphere
       ( volume
       , area
       ) where

volume :: Float -> Float  
volume radius = (4.0 / 3.0) * pi * (radius ^ 3)  

area :: Float -> Float  
area radius = 4 * pi * (radius ^ 2)
