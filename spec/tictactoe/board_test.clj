(ns tictactoe.board-test
  (:require [speclj.core :refer :all]
            [tictactoe.board :as board]))

(describe "Board"

  (describe "empty-board"
    (it "creates an empty board"
      (should= [" " " " " "
                " " " " " "
                " " " " " "]
               (board/empty-board))))

  (describe "make-move"
    (it "makes a move on the board"
      (should= [" " " " " "
                " " "X" " "
                " " " " " "]
               (board/make-move (board/empty-board) 4 "X"))))

  (describe "valid-move?"
    (it "checks if a move is valid"
      (let [board [" " " " " "
                   " " "X" " "
                   " " " " " "]]
        (should (board/valid-move? board 0))
        (should-not (board/valid-move? board 4))))))
