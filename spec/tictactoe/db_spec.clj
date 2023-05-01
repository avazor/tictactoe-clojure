(ns tictactoe.db-spec
  (:require [speclj.core :refer :all]
            [tictactoe.db :as db]))

(defn with-test-db [block]
  (let [prev-db-spec (tictactoe.db/get-db-spec)]
    (tictactoe.db/set-db-spec! db/in-memory-db-spec)
    (db/setup-db)
    (block)
    (db/teardown-db)
    (tictactoe.db/set-db-spec! prev-db-spec)))

(describe "TicTacToe DB"
  (tags :slow)
  (around [it] (with-test-db it))

  (it "can save a game"
    (let [game-id (db/save-game "test-game" "player-x" "player-o" 3)]
      (should-not (nil? game-id))))

    (it "retrieves unfinished games"
      (let [game-id (db/save-game "Unfinished Game" "player-x" "player-o" 3)]
        (db/update-game-status game-id false)
        (should= 1 (count (db/fetch-unfinished-games)))))

    (it "retrieves finished games"
      (let [game-id (db/save-game "Finished Game" "player-x" "player-o" 3)]
        (db/update-game-status game-id true)
        (should= 1 (count (db/fetch-finished-games)))))

    (it "retrieves the last move in a game"
      (let [game-id (db/save-game "Last Move Test" "player-x" "player-o" 3)]
        (db/save-move game-id 1 "X" 1)
        (db/save-move game-id 2 "O" 2)
        (should= {:id 2, :game_id 1, :move_number 2, :player "O", :position 2} (db/get-last-move game-id))))

    (it "deletes all moves associated with a game"
      (let [game-id (db/save-game "Delete Moves Test" "player-x" "player-o" 3)]
        (db/save-move game-id 1 "X" 1)
        (db/save-move game-id 2 "O" 2)
        (db/delete-moves game-id)
        (should= [" " " " " "] (db/fetch-board game-id))))

    (it "resets a game by deleting all moves and setting the game status to unfinished"
      (let [game-id (db/save-game "Reset Game Test" "player-x" "player-o" 3)]
        (db/save-move game-id 1 "X" 1)
        (db/save-move game-id 2 "O" 2)
        (db/update-game-status game-id true)
        (db/reset-game game-id)
        (should= [" " " " " "] (db/fetch-board game-id))
        (let [unfinished-games (db/fetch-unfinished-games)]
          (should= 1 (count unfinished-games))
          (should= game-id (-> unfinished-games first :id))))))