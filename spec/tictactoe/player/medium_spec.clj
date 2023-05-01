(ns tictactoe.player.medium-spec
  (:require [speclj.core :refer :all]
            [tictactoe.player.easy :as easy]
            [tictactoe.player.hard :as hard]
            [tictactoe.player.medium :as medium]
            [tictactoe.player.player :as player]))

(describe "tictactoe.player.medium"
  (it "makes a random move between easy and hard AI"
    (let [test-board ["X" "O" "X"
                      " " "O" " "
                      " " " " " "]
          medium-ai (medium/map->MediumAI {})]
      (with-redefs [rand (fn [] 0)
                    easy/map->EasyAI (fn [] (easy/->EasyAI))
                    hard/minimax-move (fn [_ _] 7)]
        (should= 7 (player/make-move medium-ai test-board "X")))
      )))
