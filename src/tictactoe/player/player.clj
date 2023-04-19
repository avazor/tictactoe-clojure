(ns tictactoe.player.player)

(defprotocol PlayerStrategy
  (make-move [this board symbol]))

