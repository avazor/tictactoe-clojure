(ns tictactoe.player.medium
  (:require [tictactoe.player.easy :as easy-ai]
            [tictactoe.player.hard :as hard-ai]
            [tictactoe.player.player :as strategy]))

(defrecord MediumAI []
  strategy/PlayerStrategy
  (make-move [this board symbol]
    (let [easy-ai (easy-ai/->EasyAI)]
      (if (< (rand) 0.5)
        (hard-ai/minimax-move board symbol)
        (strategy/make-move easy-ai board symbol)))

    ))
