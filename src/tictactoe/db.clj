(ns tictactoe.db
  (:require [clojure.java.jdbc :as jdbc])
  (:import (java.util Date)))

(def db-spec {:dbtype   "mysql"
              :host     "sql9.freemysqlhosting.net"
              :port     3306
              :dbname   "sql9613649"
              :user     "sql9613649"
              :password "Ya3QwmjI3H"})


(defn save-game [game-name player-x player-o]
  (let [result (jdbc/insert! db-spec :games {:name       game-name
                                             :player_x   player-x
                                             :player_o   player-o
                                             :created_at (Date.)})]
    (:generated_key (first result))))

(defn save-move [game-id move-number player position]
  (jdbc/insert! db-spec :moves {:game_id     game-id
                                :move_number move-number
                                :player      player
                                :position    position}))