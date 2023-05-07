(ns tictactoe.game.ui-spec
  (:require [speclj.core :refer :all]
            [tictactoe.cli :as io]
            [tictactoe.game.ui :as ui]
            [tictactoe.game.gui :as gui]))

(def test-input "2\n")
(def test-output (atom ""))

(describe "Console Game UI"
  (it "check for player selection"
    (with-redefs [io/print-message (fn [_])]
      (let [player {:player #tictactoe.player.human.Human{}, :type "HUMAN"}]
        (should= player (with-in-str "1\n" (ui/choose-player-type "X"))))))

  (it "checks correct player display"
    (let [players [{:symbol "X", :player #tictactoe.player.human.Human{}, :type "HUMAN"}
                   {:symbol "O", :player #tictactoe.player.hard.HardAI{}, :type "HARD_AI"}]]
      (should= "X is HUMAN\nO is HARD_AI\n" (with-out-str (ui/display-player-types players)))))

  (it "checks if corect board selected"
    (with-redefs [io/print-message (fn [_])]
      (should= 9 (with-in-str "1\n" (ui/choose-board-size)))))

  (around [it]
    (with-redefs [io/get-input (fn [] test-input)
                  io/print-message (fn [s] (swap! test-output str s "\n"))]))

      (it "prints the welcome message"
        (ui/welcome-message)
        (should= "Welcome to Tic Tac Toe!\n=======================\nPlease choose your play mode:\n"
                 @test-output))


      (it "returns the correct play mode based on user input"
        (should= :gui (ui/choose-play-mode)))

      (it "handles invalid input"
        (with-redefs [io/get-input (fn [] "3\n1\n")]
          (should= :console (ui/choose-play-mode))))

    (it "calls the appropriate function based on play mode"
      (with-redefs [ui/main-menu-console (fn [] (io/print-message "console"))
                    gui/create-new-game (fn [] (io/print-message "gui"))]
        (ui/main-menu)
        (should= "Welcome to Tic Tac Toe!\n=======================\nPlease choose your play mode:\n1. Console\n2. GUI\nEnter the number of your choice: \ngui\n"
                 @test-output)))

  )
