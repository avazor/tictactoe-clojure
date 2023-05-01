(ns tictactoe.game.core
  (:require
    [tictactoe.board :as board]
    [tictactoe.rules :as rules]
    [tictactoe.cli :as io]
    [tictactoe.db :as db]
    [tictactoe.player.player :as player]
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

(defn play-game [players board game-name & {:keys [game-id next-player]}]
  (let [board-size (board/board-size board)
        game-id (or game-id (db/save-game game-name (:type (first players)) (:type (second players)) board-size))
        [final-board winner] (play-turn game-id board (or next-player "X") players 1)]
    (io/print-board final-board)
    (flush)
    (if winner
      (io/print-message (str winner " wins!"))
      (io/print-message "It's a draw!"))
    (db/update-game-status game-id true)))
