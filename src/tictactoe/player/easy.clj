(ns tictactoe.player.easy
  (:require [tictactoe.board :as board]
            [tictactoe.player.player :as player]))

(deftype EasyAI []
  player/PlayerStrategy
  (make-move [this board symbol]
    (rand-nth (board/available-moves board))))
