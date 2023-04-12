(ns tictactoe.core
  (:require [clojure.string :as str]
            [tictactoe.board :as board]
            [tictactoe.ai-player-hard :as ai]
            [tictactoe.game :as game]
            [tictactoe.cli :as io]))

(defn human-move []
  (io/print-message "Enter the index of the cell for your next move: \n")
  (Integer/parseInt (io/get-input)))

(defn ai-move [board player]
  (io/print-message "AI is making a move...\n")
  (Thread/sleep 200)
  (ai/minimax-move board player))

(defn next-move [board current-player player1]
  (if (= current-player player1)
    (human-move)
    (ai-move board current-player)))

(defn play-turn [board current-player player1 player2]
  (io/print-board board)
  (let [index (next-move board current-player player1)]
    (if (board/valid-move? board index)
      (let [new-board (board/make-move board index current-player)]
        (if (game/game-over? new-board)
          (if (game/has-winner? new-board)
            [new-board current-player]
            [new-board nil])
          (play-turn new-board (game/next-player current-player) player1 player2)))
      (do (io/print-message "Invalid move. Try again.")
          (play-turn board current-player player1 player2)))))

(defn -main [& args]
  (io/print-message "Choose your player (X or O): ")
  (let [user-input (str/upper-case (io/get-input))
        player1 (if (= user-input "X") "X" "O")
        player2 (if (= user-input "X") "O" "X")]
    (io/print-message (str "You are " player1))
    (io/print-message (str "AI is " player2))
    (let [[final-board winner] (play-turn (board/empty-board) "X" player1 player2)]
      (io/print-board final-board)
      (if winner
        (io/print-message (str winner " wins!"))
        (io/print-message "It's a draw!")))))
