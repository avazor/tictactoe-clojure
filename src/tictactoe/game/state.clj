(ns tictactoe.game.state
  (:require
    [tictactoe.db :as db]
    [tictactoe.board :as board]
    [tictactoe.game.core :as core]
    [tictactoe.cli :as io]
    [tictactoe.player.easy :as easy-ai]
    [tictactoe.player.medium :as medium-ai]
    [tictactoe.player.human :as human]
    [tictactoe.player.hard :as hard-ai]
    ))

(defn player-from-type [player-type]
  (case player-type
    "HUMAN" {:player (human/->Human) :type "HUMAN"}
    "EASY_AI" {:player (easy-ai/->EasyAI) :type "EASY_AI"}
    "MEDIUM_AI" {:player (medium-ai/->MediumAI) :type "MEDIUM_AI"}
    "HARD_AI" {:player (hard-ai/->HardAI) :type "HARD_AI"}))

(defn create-players-from-types [player-x-type player-o-type]
  (let [player1 (merge {:symbol "X"} (player-from-type player-x-type))
        player2 (merge {:symbol "O"} (player-from-type player-o-type))]
    [player1 player2]))

(defn resume-game []
  (let [unfinished-games (db/fetch-unfinished-games)]
    (if (empty? unfinished-games)
      (io/print-message "No unfinished games found.")
      (do
        (io/print-message "Select an unfinished game:")
        (doseq [[index game] (map-indexed vector unfinished-games)]
          (io/print-message (str index " - " (:name game))))
        (let [game-index (Integer/parseInt (io/get-input))
              selected-game (nth unfinished-games game-index)
              game-id (:id selected-game)
              last-move (db/get-last-move game-id)
              next-player (if (= (:player last-move) "X") "O" "X")
              players (create-players-from-types (:player_x selected-game) (:player_o selected-game))
              board (db/fetch-board game-id)]
          (core/play-game players board (:name selected-game) :game-id game-id :next-player next-player))))))

(defn restart-game []
  (let [games (db/fetch-finished-games)]
    (if (empty? games)
      (io/print-message "No finished games found.")
      (do
        (io/print-message "Select a finished game to restart:")
        (doseq [game games]
          (io/print-message (str (:id game) " - " (:name game))))
        (let [game-id (Integer/parseInt (io/get-input))
              game-info (first (filter #(= (:id %) game-id) games))
              players (create-players-from-types (:player_x game-info) (:player_o game-info))
              board-size (int (Math/sqrt (count (:board game-info))))
              empty-board (board/empty-board board-size)
              game-name (:name game-info)]
          (db/reset-game game-id)
          (core/play-game players empty-board game-name :game-id game-id))))))
