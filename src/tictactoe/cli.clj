(ns tictactoe.cli
  (:require [tictactoe.board :as board]))

(defn print-message [msg]
  (println msg))

(defn get-input []
  (read-line))

(defn print-2d-board [board]
  (let [size (int (Math/sqrt (count board)))
        divider (apply str (repeat (* size 5) "-"))]
    (dotimes [row size]
      (dotimes [col size]
        (let [index (+ (* row size) col)
              cell (board index)]
          (print (if (= cell " ")
                   (format "%2d " index)
                   (format " %s " cell))))
        (if (< col (dec size)) (print " | ")))
      (when (< row (dec size)) (println "\n" divider)))
    (println "\n")
    (flush)))

(defn print-3d-board [board]
  (let [size (int (Math/pow (count board) (/ 1 3)))]
    (dotimes [layer size]
      (println (str "Layer " (inc layer) ":"))
      (dotimes [row size]
        (dotimes [col size]
          (let [index (+ (* layer size size) (* row size) col)
                cell (board index)]
            (print (if (= cell " ")
                     (format "%2d " index)
                     (format " %s " cell))))
          (if (< col (dec size)) (print " | ")))
        (println))
      (println))
    (flush))
  )

(defn print-board [game-board]
  (let [board-size (board/board-size game-board)]
    (case board-size
      9 (print-2d-board game-board)
      16 (print-2d-board game-board)
      27 (print-3d-board game-board)
      (println "Invalid board size"))))

