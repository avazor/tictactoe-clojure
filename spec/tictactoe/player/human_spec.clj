(ns tictactoe.player.human-spec
  (:require [speclj.core :refer :all]
            [tictactoe.cli :as io]
            [tictactoe.player.human :as human]
            [tictactoe.player.player :as strategy]))

(describe "tictactoe.player.human"
  (it "prompts for a move and returns the parsed user input"
    (with-redefs [io/print-message (fn [_] :message-printed)
                  io/get-input (fn [] "5")]
      (let [human-player (human/map->Human {})]
        (with-out-str
          (should= :message-printed (io/print-message "Enter the index of the cell for your next move: \n")))
        (should= 5 (strategy/make-move human-player nil nil))))))
