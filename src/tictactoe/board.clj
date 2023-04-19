(ns tictactoe.board)

(defn empty-board [size]
  (vec (repeat (* size size) " ")))

(defn get-cell [board index]
  (nth board index))

(defn available-moves [board]
  (filter #(= " " (get-cell board %)) (range (count board))))

(defn make-move [board index player]
  (assoc board index player))

(defn valid-move? [board index]
  (= " " (get-cell board index)))

(defn full? [board]
  (not-any? #{" "} board))

(defn board-size [board]
  (int (Math/sqrt (count board))))