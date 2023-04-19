(ns tictactoe.game
  (:require
    [tictactoe.player.hard :as hard-ai]
    [tictactoe.board :as board]
    [tictactoe.cli :as io]
    [tictactoe.rules :as rules]
    [tictactoe.player.easy :as easy-ai]
    [tictactoe.player.medium :as medium-ai]
    [tictactoe.player.human :as human]
    [tictactoe.player.player :as player]
    [tictactoe.db :as db]
    ))

(defn next-move [board current-player players]
  (let [player (if (= current-player (:symbol (first players))) (first players) (second players))
        symbol (get player :symbol)
        player (get player :player)]
    (player/make-move player board symbol)))

(defn play-turn [game-id board current-player players move-number]
  (io/print-board board)
  (let [index (next-move board current-player players)]
    (if (board/valid-move? board index)
      (let [new-board (board/make-move board index current-player)]
        (db/save-move game-id move-number current-player index)
        (if (rules/game-over? new-board)
          (if (rules/get-winner new-board)
            [new-board current-player]
            [new-board nil])
          (play-turn game-id new-board (rules/get-next-player current-player) players (inc move-number))))
      (do (io/print-message "Invalid move. Try again.")
          (play-turn game-id board current-player players move-number)))))

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
    (io/print-message (str (:symbol player) " is " (if (= (:type player) :human) "HUMAN" "AI")))))

(defn choose-board-size []
  (io/print-message "Choose board size (3 for 3x3, 4 for 4x4): ")
  (let [user-input (Integer/parseInt (io/get-input))]
    (if (#{3 4} user-input) user-input (do (io/print-message "Invalid input. Try again.") (choose-board-size)))))

(defn play-game [players board-size game-name]
  (let [game-id (db/save-game game-name (:type (first players)) (:type (second players)))
        [final-board winner] (play-turn game-id (board/empty-board board-size) "X" players 1)]
    (io/print-board final-board)
    (flush)
    (if winner
      (io/print-message (str winner " wins!"))
      (io/print-message "It's a draw!"))))


(defn get-game-name []
  (println "Enter the game name:")
  (read-line))