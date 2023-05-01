(ns tictactoe.rules
  (:require [tictactoe.board :as board]))

(defn winning-positions [size]
  (let [rows (map (fn [i] (take size (iterate inc (* i size)))) (range size))
        cols (map (fn [i] (take size (iterate (fn [x] (+ x size)) i))) (range size))
        diag1 [(take size (iterate (fn [x] (+ x (inc size))) 0))]
        diag2 [(take size (iterate (fn [x] (+ x (dec size))) (dec size)))]]
    (concat rows cols diag1 diag2)))

(defn winning-positions-3d []
  '((0 1 2) (3 4 5) (6 7 8) (9 10 11) (12 13 14) (15 16 17) (18 19 20) (21 22 23) (24 25 26)
    (0 3 6) (1 4 7) (2 5 8) (9 12 15) (10 13 16) (11 14 17) (18 21 24) (19 22 25) (20 23 26)
    (0 9 18) (1 10 19) (2 11 20) (3 12 21) (4 13 22) (5 14 23) (6 15 24) (7 16 25) (8 17 26)
    (0 4 8) (2 4 6) (9 13 17) (11 13 15) (18 22 26) (20 22 24)
    (0 13 26) (2 13 24) (6 13 20) (8 13 18) (3 13 23) (1 13 25) (7 13 19) (5 13 21)))

(defn get-winning-positions [size]
  (cond
    (= size 9) (winning-positions 3)
    (= size 16) (winning-positions 4)
    (= size 27) (winning-positions-3d)))
(defn winning-condition? [board winning-positions]
  (some (fn [winning-pos]
          (let [player (nth board (first winning-pos))]
            (if (not= player " ")
              (when (every? (fn [index] (= player (nth board index))) winning-pos)
                player)
              false)))
        winning-positions))

(defn get-winner [board]
  (let [count (count board)
        winning-positions (get-winning-positions count)]
    (winning-condition? board winning-positions)))

(defn game-over? [board]
  (or (get-winner board) (board/full? board)))

(defn get-next-player [player]
  (if (= player "X") "O" "X"))
