(ns tictactoe.board-test
  (:require [speclj.core :refer :all]
            [tictactoe.board :as board]))

(describe "Board"
    (it "creates an empty board"
      (should= [" " " " " "
                " " " " " "
                " " " " " "]
               (board/empty-board 9)))
  (it "gets the value of a cell in the board"
    (let [board [" " " " " "
                 " " "X" " "
                 " " " " " "]]
      (should= "X" (board/get-cell board 4))
      (should= " " (board/get-cell board 0))))
  (it "returns the indices of available moves"
    (let [board [" " "X" "O"
                 " " "X" " "
                 " " "O" " "]]
      (should= [0 3 5 6 8] (board/available-moves board))))
    (it "makes a move on the board"
      (should= [" " " " " "
                " " "X" " "
                " " " " " "]
               (board/make-move (board/empty-board 9) 4 "X")))
    (it "checks if a move is valid"
      (let [board [" " " " " "
                   " " "X" " "
                   " " " " " "]]
        (should (board/valid-move? board 0))
        (should-not (board/valid-move? board 4))))
    (it "checks if board is full"
      (let [board ["O" "X" "O"
                   "O" "X" "X"
                   "X" "O" "X"]]
        (should (board/full? board))))
    (it "checks that board is not full"
      (let [board ["O" "X" "O"
                   "O" "X" "X"
                   "X" "O" " "]]
        (should-not (board/full? board))))
  (it "returns the size of the board"
    (let [board [" " " " " "
                 " " "X" " "
                 " " " " " "]]
      (should= 9 (board/board-size board))))
  )
