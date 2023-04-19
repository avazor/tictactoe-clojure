(ns tictactoe.rules
  (:require [tictactoe.board :as board]))

(defn winning-positions [size]
  (let [rows (map (fn [i] (take size (iterate inc (* i size)))) (range size))
        cols (map (fn [i] (take size (iterate (fn [x] (+ x size)) i))) (range size))
        diag1 [(take size (iterate (fn [x] (+ x (inc size))) 0))]
        diag2 [(take size (iterate (fn [x] (+ x (dec size))) (dec size)))]]
    (concat rows cols diag1 diag2)))


(defn get-winner [board]
  (let [size (int (Math/sqrt (count board)))
        winning-positions (winning-positions size)]
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
