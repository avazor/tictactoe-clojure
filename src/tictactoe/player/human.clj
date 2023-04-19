(ns tictactoe.player.human
  (:require [tictactoe.player.player :as strategy]
            [tictactoe.cli :as io]))

(defrecord Human []
  strategy/PlayerStrategy
  (make-move [_ _ _]
    (io/print-message "Enter the index of the cell for your next move: \n")
    (Integer/parseInt (io/get-input))))
