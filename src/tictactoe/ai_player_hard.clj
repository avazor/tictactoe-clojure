(ns tictactoe.ai-player-hard
  (:require [tictactoe.board :as board]
            [tictactoe.game :as game]))

(defn get-score [winner depth player]
  (cond
    (= winner player) (- 10 depth)
    (= winner (game/next-player player)) (- depth 10)
    :else 0))

(defn calculate-best-score [player ai-player scores]
  (if (= player ai-player)
    (apply max scores)
    (apply min scores)))

(defn minimax [board depth player ai-player]
  (let [next-player (game/next-player player)
        winner (game/has-winner? board)]
    (if (game/game-over? board)
      (get-score winner depth player)
      (let [moves (board/available-moves board)
            scores (map (fn [move] (minimax (board/make-move board move player) (inc depth) next-player ai-player)) moves)]
        (calculate-best-score player ai-player scores)))))

(defn minimax-move [board ai-player]
  (let [player (game/next-player ai-player)
        next-moves (board/available-moves board)
        scores (map #(minimax (board/make-move board % player) 0 (game/next-player player) ai-player) next-moves)
        best-score (calculate-best-score player ai-player scores)]
    (nth next-moves (.indexOf scores best-score))))
