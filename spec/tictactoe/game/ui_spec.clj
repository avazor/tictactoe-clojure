(ns tictactoe.game.ui-spec
  (:require [speclj.core :refer :all]
            [tictactoe.cli :as io]
            [tictactoe.game.ui :as ui]))

(describe "Game UI"
  (it "check for player selection"
    (with-redefs [io/print-message (fn [_] )]
    (let [player {:player #tictactoe.player.human.Human{}, :type "HUMAN"}]
      (should= player (with-in-str "1\n" (ui/choose-player-type "X"))))))

  (it "checks correct player display"
    (let [players [{:symbol "X", :player #tictactoe.player.human.Human{}, :type "HUMAN"}
                   {:symbol "O", :player #tictactoe.player.hard.HardAI{}, :type "HARD_AI"}]]
    (should= "X is HUMAN\nO is HARD_AI\n" (with-out-str(ui/display-player-types players) ))))

  (it "checks if corect board selected"
    (with-redefs [io/print-message (fn [_] )]
    (should= 9 (with-in-str "1\n" (ui/choose-board-size)))))
  )
