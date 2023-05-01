(ns tictactoe.game.state-spec
  (:require [speclj.core :refer :all]
            [tictactoe.game.core :as core]
            [tictactoe.game.state :as state]
            [tictactoe.board :as board]
            [tictactoe.db :as db]
            [tictactoe.cli :as io]))
(describe "tictactoe.game.state"
  (it "resumes a game if there are any unfinished games"
    (with-redefs [db/fetch-unfinished-games (fn [] [{:id 1, :name "unfinished game", :player_x "HUMAN", :player_o "EASY_AI"}])
                  db/get-last-move (fn [game-id] {:player "X"})
                  db/fetch-board (fn [game-id] (board/empty-board 9))
                  io/get-input (fn [] "0")
                  io/print-message (fn [msg] nil)
                  core/play-game (fn [players board game-name & {:keys [game-id next-player]}] true)]
      (should= (state/resume-game) true)))

  (it "prints a message if there are no unfinished games"
    (with-redefs [db/fetch-unfinished-games (fn [] [])
                  io/print-message (fn [msg] (if (= msg "No unfinished games found.") :no-games-found))]
      (should= (state/resume-game) :no-games-found)))

  (it "restarts a finished game"
    (let [dummy-games [{:id 1 :name "Game 1" :player_x "HUMAN" :player_o "EASY_AI" :board (repeat 9 " ")}]]
      (with-redefs [db/fetch-finished-games (fn [] dummy-games)
                    io/get-input (fn [] "1")
                    db/reset-game (fn [_] nil)
                    core/play-game (fn [_ _ _ & _] :game-played)
                    io/print-message (fn [_])]
        (should= :game-played (state/restart-game))))
    (with-redefs [db/fetch-finished-games (fn [] [])
                  io/print-message (fn [& _] :no-finished-games)]
      (should= :no-finished-games (state/restart-game)))))