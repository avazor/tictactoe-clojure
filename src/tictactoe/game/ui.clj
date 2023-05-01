(ns tictactoe.game.ui
  (:require
    [tictactoe.cli :as io]
    [tictactoe.player.easy :as easy-ai]
    [tictactoe.player.medium :as medium-ai]
    [tictactoe.player.human :as human]
    [tictactoe.player.hard :as hard-ai]
    [tictactoe.game.core :as core]
    [tictactoe.game.state :as state]
    [tictactoe.board :as board]
    ))

(defn choose-player-type [player]
  (io/print-message (str "Choose player type for " player " (1 for HUMAN, 2 for EASY AI, 3 for MEDIUM AI, 4 for HARD AI): "))
  (let [user-input (Integer/parseInt (io/get-input))]
    (case user-input
      1 {:player (human/->Human) :type "HUMAN"}
      2 {:player (easy-ai/->EasyAI) :type "EASY_AI"}
      3 {:player (medium-ai/->MediumAI) :type "MEDIUM_AI"}
      4 {:player (hard-ai/->HardAI) :type "HARD_AI"})))

(defn create-players []
  (let [player1 (merge {:symbol "X"} (choose-player-type "X"))
        player2 (merge {:symbol "O"} (choose-player-type "O"))]
    [player1 player2]))

(defn display-player-types [players]
  (doseq [player players]
    (io/print-message (str (:symbol player) " is " (:type player) ))))

(defn choose-board-size []
  (io/print-message "Choose board size (1 for 3x3, 2 for 4x4, 3 for 3x3x3): ")
  (let [user-input (Integer/parseInt (io/get-input))]
    (case user-input
      1 9
      2 16
      3 27
      (do (io/print-message "Invalid input. Try again.") (choose-board-size)))))

(defn get-game-name []
  (io/print-message "Enter the game name:")
  (io/get-input))

(defn main-menu []
  (io/print-message "Select an option: ")
  (io/print-message "1. Start a new game")
  (io/print-message "2. Resume an unfinished game")
  (io/print-message "3. Restart a finished game")
  (let [user-choice (Integer/parseInt (io/get-input))]
    (case user-choice
      1 (do
          (let [players (create-players)
                size (choose-board-size)
                empty-board (board/empty-board size)
                game-name (get-game-name)]
            (display-player-types players)
            (core/play-game players empty-board game-name)))
      2 (state/resume-game)
      3 (state/restart-game)
      (io/print-message "Invalid option. Please try again."))))