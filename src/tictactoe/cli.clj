(ns tictactoe.cli)

(defn print-message [msg]
  (println msg))

(defn get-input []
  (read-line))

(defn print-board [board]
  (dotimes [row 3]
    (dotimes [col 3]
      (let [index (+ (* row 3) col)]
        (print (if (= (board index) " ")
                 index
                 (board index))))
      (if (< col 2) (print " | ")))
    (when (< row 2) (println "\n---------")))
  (println "")
  (flush))