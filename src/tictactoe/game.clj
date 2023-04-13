(ns tictactoe.game
  (:require[tictactoe.board :as board]
           [tictactoe.ai-player-hard :as ai]
           [tictactoe.cli :as io]
           [tictactoe.rules :as rules]))

(defn human-move []
  (io/print-message "Enter the index of the cell for your next move: \n")
  (Integer/parseInt (io/get-input)))

(defn ai-move [board player]
  (io/print-message (str (:symbol player) " is making a move...\n"))
  (ai/minimax-move board (:symbol player)))

(defn next-move [board current-player players]
  (let [player (if (= current-player (:symbol (first players))) (first players) (second players))]
    (if (= (:type player) :human)
      (human-move)
      (ai-move board player))))

(defn play-turn [board current-player players]
  (io/print-board board)
  (let [index (next-move board current-player players)]
    (if (board/valid-move? board index)
      (let [new-board (board/make-move board index current-player)]
        (if (rules/game-over? new-board)
          (if (rules/get-winner new-board)
            [new-board current-player]
            [new-board nil])
          (play-turn new-board (rules/get-next-player current-player) players)))
      (do (io/print-message "Invalid move. Try again.")
          (play-turn board current-player players)))))

(defn choose-player-type [player]
  (io/print-message (str "Choose player type for " player " (1 for HUMAN, 2 for AI): "))
  (let [user-input (Integer/parseInt (io/get-input))]
    (if (= user-input 1) :human :ai)))

(defn create-players []
  (let [player1 {:symbol "X" :type (choose-player-type "X")}
        player2 {:symbol "O" :type (choose-player-type "O")}]
    [player1 player2]))

(defn display-player-types [players]
  (doseq [player players]
    (io/print-message (str (:symbol player) " is " (if (= (:type player) :human) "HUMAN" "AI")))))

(defn play-game [players]
  (let [[final-board winner] (play-turn (board/empty-board) "X" players)]
    (io/print-board final-board)
    (if winner
      (io/print-message (str winner " wins!"))
      (io/print-message "It's a draw!"))))