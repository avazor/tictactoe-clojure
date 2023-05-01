(ns tictactoe.player.hard-spec
  (:require [speclj.core :refer :all]
            [tictactoe.board :as board]
            [tictactoe.player.player :as strategy]
            [tictactoe.rules :as rules]
            [tictactoe.player.hard :as hard-ai])
  (:import (tictactoe.player.hard HardAI)))

(describe "HardAI"
  (let [ai (HardAI.)]
    (describe "make-move"
      (it "AI makes a winning move"
        (let [current-board ["X" " " " "
                             "O" "O" " "
                             "X" "X" " "]
              ai-player "O"
              move (strategy/make-move ai current-board ai-player)
              new-board (board/make-move current-board move ai-player)
              winner (rules/get-winner new-board)]
          (should= ai-player winner)))

      (it "AI blocks opponent's winning move"
        (let [current-board ["X" "X" " "
                             "O" "O" " "
                             "X" " " " "]
              ai-player "X"
              move (strategy/make-move ai current-board ai-player)
              new-board (board/make-move current-board move ai-player)]
          (should-not= (rules/get-winner new-board) (rules/get-next-player ai-player))))

      (it "AI selects a valid move"
        (let [current-board ["X" " " " "
                             "O" " " " "
                             " " " " " "]
              ai-player "X"
              move (strategy/make-move ai current-board ai-player)]
          (should= " " (board/get-cell current-board move)))

        (let [current-board ["O" "X" "O"
                             "X" "O" "X"
                             "X" "O" " "]
              ai-player "O"]
          (should= (strategy/make-move ai current-board ai-player) 8)))))
)

