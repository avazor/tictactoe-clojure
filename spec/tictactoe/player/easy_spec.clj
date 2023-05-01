(ns tictactoe.player.easy-spec
  (:require [speclj.core :refer :all]
            [tictactoe.board :as board]
            [tictactoe.player.easy :as easy]
            [tictactoe.player.player :as strategy]))
(describe "tictactoe.player.easy"
  (it "makes a random move from the available moves on the board"
    (let [test-board ["X" "O" "X"
                      " " "O" " "
                      " " " " " "]
          easy-ai (easy/map->EasyAI {})
          available-moves (board/available-moves test-board)]
      (with-redefs [rand-nth (fn [coll] (first coll))]
        (should (some #{(strategy/make-move easy-ai test-board "X")} available-moves))))))