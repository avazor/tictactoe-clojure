(ns tictactoe.db
  (:require [clojure.java.jdbc :as jdbc]
            [tictactoe.board :as board])
  (:import (java.util Date)))

(def mysql-db-spec {:dbtype   "mysql"
                    :host     "sql9.freemysqlhosting.net"
                    :port     3306
                    :dbname   "sql9613649"
                    :user     "sql9613649"
                    :password "Ya3QwmjI3H"})

(def in-memory-db-spec {:dbtype "h2"
                        :dbname "mem:test-db;DB_CLOSE_DELAY=-1"})

(def db-selector (atom mysql-db-spec))

(defn set-db-spec! [db-spec]
  (reset! db-selector db-spec))

(defn get-db-spec []
  @db-selector)

(defn setup-db []
  (jdbc/db-do-commands (get-db-spec)
                       "CREATE TABLE IF NOT EXISTS games (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), player_x VARCHAR(255), player_o VARCHAR(255), board_size INT, created_at TIMESTAMP, finished BOOLEAN)")
  (jdbc/db-do-commands (get-db-spec)
                       "CREATE TABLE IF NOT EXISTS moves (id INT AUTO_INCREMENT PRIMARY KEY, game_id INT, move_number INT, player VARCHAR(1), position INT, FOREIGN KEY (game_id) REFERENCES games(id))"))

(defn teardown-db []
  (jdbc/execute! (get-db-spec) ["DROP TABLE IF EXISTS moves"])
  (jdbc/execute! (get-db-spec) ["DROP TABLE IF EXISTS games"]))

(defn save-game [game-name player-x player-o board-size]
  (let [results (jdbc/insert! (get-db-spec) :games {:name       game-name
                                                    :player_x   player-x
                                                    :player_o   player-o
                                                    :board_size board-size
                                                    :created_at (Date.)
                                                    :finished   false})
        result (first results)]
    (if (contains? result :generated_key)
      (:generated_key result)
      (:id result))))

(defn update-game-status [game-id finished?]
  (jdbc/update! (get-db-spec) :games {:finished finished?} ["id = ?" game-id]))

(defn save-move [game-id move-number player position]
  (jdbc/insert! (get-db-spec) :moves {:game_id     game-id
                                :move_number move-number
                                :player      player
                                :position    position}))

(defn fetch-board [game-id]
  (let [moves (jdbc/query (get-db-spec) ["SELECT * FROM moves WHERE game_id = ? ORDER BY move_number" game-id])
        board-size (-> (jdbc/query (get-db-spec) ["SELECT board_size FROM games WHERE id = ?" game-id]) first :board_size)
        empty-board (board/empty-board board-size)]
    (reduce (fn [board move] (assoc board (:position move) (:player move))) empty-board moves)))

(defn fetch-unfinished-games []
  (map #(assoc % :board (fetch-board (:id %))) (jdbc/query (get-db-spec) ["SELECT * FROM games WHERE finished = false"])))

(defn fetch-finished-games []
  (map #(assoc % :board (fetch-board (:id %))) (jdbc/query (get-db-spec) ["SELECT * FROM games WHERE finished = true"])))

(defn get-last-move [game-id]
  (first (jdbc/query (get-db-spec) ["SELECT * FROM moves WHERE game_id = ? ORDER BY move_number DESC LIMIT 1" game-id])))

(defn delete-moves [game-id]
  (jdbc/delete! (get-db-spec) :moves ["game_id = ?" game-id]))

(defn reset-game [game-id]
  (delete-moves game-id)
  (update-game-status game-id false))
