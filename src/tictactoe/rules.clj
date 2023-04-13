(ns tictactoe.rules
  (:require [tictactoe.board :as board]))
(defn get-winner [board]
  (let [winning-positions [[0 1 2] [3 4 5] [6 7 8] [0 3 6] [1 4 7] [2 5 8] [0 4 8] [2 4 6]]]
    (some (fn [winning-pos]
            (let [player (nth board (first winning-pos))]
              (if (not= player " ")
                (when (every? (fn [index] (= player (nth board index))) winning-pos)
                  player)
                false)))
          winning-positions)))

(defn game-over? [board]
  (or (get-winner board) (board/full? board)))

(defn get-next-player [player]
  (if (= player "X") "O" "X"))