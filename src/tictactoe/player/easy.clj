(ns tictactoe.player.easy
  (:require [tictactoe.board :as board]
            [tictactoe.player.player :as strategy]))

(defrecord EasyAI []
  strategy/PlayerStrategy
  (make-move [this board symbol]
    (rand-nth (board/available-moves board))))
