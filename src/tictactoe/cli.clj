(ns tictactoe.cli)

(defn print-message [msg]
  (println msg))

(defn get-input []
  (read-line))

(defn print-board [board]
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

