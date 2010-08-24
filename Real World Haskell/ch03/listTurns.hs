module ListTurns where

import CalculateTurn

listTurns (a:(b:(c:xs))) = (calculateTurn a b c):(listTurns (b:(c:xs)))
listTurns _ = []
