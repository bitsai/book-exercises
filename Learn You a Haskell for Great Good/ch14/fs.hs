import Data.List (break)

type Name = String
type Data = String
data FSItem = File Name Data | Folder Name [FSItem] deriving (Show)

data FSCrumb = FSCrumb Name [FSItem] [FSItem] deriving (Show)
type FSZipper = (FSItem, [FSCrumb])

fsUp :: FSZipper -> Maybe FSZipper
fsUp (item, (FSCrumb name ls rs):bs) = Just (Folder name (ls ++ [item] ++ rs), bs)
fsUp (item, []) = Just (item, [])

fsTo :: Name -> FSZipper -> Maybe FSZipper
fsTo _ (File _ _, _) = Nothing
fsTo name (Folder folderName items, bs) =
  case break (nameIs name) items of
    (ls, item:rs) -> Just (item, (FSCrumb folderName ls rs):bs)
    _             -> Nothing

nameIs :: Name -> FSItem -> Bool
nameIs name (Folder itemName _) = name == itemName
nameIs name (File itemName _) = name == itemName

fsRename :: Name -> FSZipper -> Maybe FSZipper
fsRename newName (Folder _ items, bs) = Just (Folder newName items, bs)
fsRename newName (File _ dat, bs) = Just (File newName dat, bs)

fsNewFile :: FSItem -> FSZipper -> Maybe FSZipper
fsNewFile _ (File _ _, _) = Nothing
fsNewFile item (Folder folderName items, bs) = 
  Just (Folder folderName (item:items), bs)

myDisk :: FSItem
myDisk =
    Folder "root"
        [ File "goat_yelling_like_man.wmv" "baaaaaa"
        , File "pope_time.avi" "god bless"
        , Folder "pics"
            [ File "ape_throwing_up.jpg" "bleargh"
            , File "watermelon_smash.gif" "smash!!"
            , File "skull_man(scary).bmp" "Yikes!"
            ]
        , File "dijon_poupon.doc" "best mustard"
        , Folder "programs"
            [ File "fartwizard.exe" "10gotofart"
            , File "owl_bandit.dmg" "mov eax, h00t"
            , File "not_a_virus.exe" "really not a virus"
            , Folder "source code"
                [ File "best_hs_prog.hs" "main = print (fix error)"
                , File "random.hs" "main = print 4"
                ]
            ]
        ]
