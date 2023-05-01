(ns tictactoe.cli-test
  (:require [speclj.core :refer :all]
            [tictactoe.cli :as cli]))

(describe "tictactoe.cli"
    (it "prints the given message"
      (should= "Hello, world!\n"
               (with-out-str (cli/print-message "Hello, world!"))))

    (it "prints the board 3x3"
      (let [board ["X" "O" "X"
                   "O" "X" "O"
                   " " " " "O"]]
        (should= (str " X  |  O  |  X \n ---------------\n O  |  X  |  O \n ---------------\n 6  |  7  |  O \n\n")
                 (with-out-str (cli/print-board board)))))
  (it "prints the board 4x4"
    (let [board ["X" "O" "X" "X"
                 "O" "X" "O" "O"
                 " " " " "O" " "
                 " " " " " " " "]]
      (should= (str " X  |  O  |  X  |  X \n --------------------\n O  |  X  |  O  |  O \n --------------------\n 8  |  9  |  O  | 11 \n --------------------\n12  | 13  | 14  | 15 \n\n")
               (with-out-str (cli/print-board board)))))

    (it "reads user input"

      (with-in-str "4\n"
                   (should= "4" (cli/get-input)))))
