(ns tictactoe.core
  (:require [tictactoe.game :as game]))

(defn -main [& args]
  (let [players (game/create-players)]
    (game/display-player-types players)
    (game/play-game players)))
