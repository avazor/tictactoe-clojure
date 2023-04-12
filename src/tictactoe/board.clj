(ns tictactoe.board)

(defn empty-board []
  (vec (repeat 9 " ")))

(defn available-moves [board]
  (filter #(= " " (nth board %)) (range 9)))

(defn make-move [board index player]
  (assoc board index player))

(defn valid-move? [board index]
  (= " " (nth board index)))

(defn full? [board]
  (not-any? #{" "} board))
