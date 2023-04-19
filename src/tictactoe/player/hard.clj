(ns tictactoe.player.hard
  (:require [tictactoe.board :as board]
            [tictactoe.rules :as rules]
            [tictactoe.player.player :as strategy]))

(declare get-scores)

(defn game-evaluation [winner ai-player]
  (cond
    (not winner) 0
    (= winner ai-player) 10
    (= winner (rules/get-next-player ai-player)) -10))

(defn minimax [board ai-player current-player]
  (let [winner (rules/get-winner board)]
    (if (rules/game-over? board)
      (game-evaluation winner ai-player)
      (let [scores (get-scores board ai-player (rules/get-next-player current-player))]
        (if (= current-player ai-player)
          (apply min scores)
          (apply max scores))))))

(defn get-scores [board ai-player next-player]
  (map #(minimax (board/make-move board % next-player) ai-player next-player)
       (board/available-moves board)))

(defn minimax-move [board ai-player]
  (println ai-player)
  (let [scores (get-scores board ai-player ai-player)
        valid-moves (board/available-moves board)]
    (nth valid-moves (.indexOf scores (apply max scores)))))

(defrecord HardAI []
  strategy/PlayerStrategy
  (make-move [this board symbol]
    (minimax-move board symbol)))