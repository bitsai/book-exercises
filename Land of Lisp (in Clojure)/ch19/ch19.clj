(use 'svg)
(use 'dod-v3)
(let [xml (with-out-str (svg 100 100 (draw-die-svg 50 50 [255 0 0])))]
  (spit "die.svg" xml))

(let [xml (with-out-str (svg 300 300 (draw-tile-svg 0 0 0 [0 3] 100 150
                                                    [255 0 0] nil)))]
  (spit "tile.svg" xml))

(let [xml (with-out-str (svg board-width board-height
                             (draw-board-svg (gen-board) nil [])))]
  (spit "board.svg" xml))

(use 'web-server)
(serve dod-request-handler)
