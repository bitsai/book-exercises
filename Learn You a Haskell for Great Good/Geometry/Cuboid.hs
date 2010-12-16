module Geometry.Cuboid  
       ( volume  
       , area  
       ) where  

volume :: Float -> Float -> Float -> Float  
volume a b c = (rectangleArea a b) * c  

area :: Float -> Float -> Float -> Float  
area a b c = (rectangleArea a b) * 2 + 
             (rectangleArea a c) * 2 + 
             (rectangleArea c b) * 2  

rectangleArea :: Float -> Float -> Float  
rectangleArea a b = a * b
