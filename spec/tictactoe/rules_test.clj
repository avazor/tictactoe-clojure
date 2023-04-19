(ns tictactoe.rules-test
  (:require [speclj.core :refer :all]
            [tictactoe.rules :as rules]))
(describe "Rules"
    (it "returns the correct winning positions for a 3x3 board"
      (should=
        '((0 1 2) (3 4 5) (6 7 8)
          (0 3 6) (1 4 7) (2 5 8)
          (0 4 8) (2 4 6))
        (rules/winning-positions 3)))

    (it "returns the correct winning positions for a 4x4 board"
      (should=
        '((0 1 2 3) (4 5 6 7) (8 9 10 11) (12 13 14 15)
          (0 4 8 12) (1 5 9 13) (2 6 10 14) (3 7 11 15)
          (0 5 10 15) (3 6 9 12))
        (rules/winning-positions 4)))
  (it "detects a winning board"
    (let [winner-board ["X" "X" "X"
                        " " "O" " "
                        "O" " " " "]]
      (should= "X" (rules/get-winner winner-board))))

  (it "detects a non-winning board"
    (let [non-winner-board ["X" "O" "X"
                            "X" "O" "O"
                            "O" "X" " "]]
      (should= nil (rules/get-winner non-winner-board))))

  (it "detects a non-winning board"
    (let [non-winner-board ["O" "O" "X"
                            "X" "X" "O"
                            "O" "X" "X"]]
      (should= nil (rules/get-winner non-winner-board))))

  (it "detects a winning diagonal"
    (let [winner-board ["X" " " " "
                        " " "X" " "
                        " " " " "X"]]
      (should= "X" (rules/get-winner winner-board))))

  (it "returns 'O' when given 'X'"
    (should= "O" (rules/get-next-player "X")))
  (it "returns 'X' when given 'O'"
    (should= "X" (rules/get-next-player "O")))


  (it "detects a game over due to a win"
    (let [winner-board ["X" "X" "X"
                        " " "O" " "
                        "O" " " " "]]
      (should= "X" (rules/game-over? winner-board))))

  (it "detects a game over due to a win"
    (let [winner-board ["X" "X" "X"
                        "O" "O" " "
                        " " " " " "]]
      (should= "X" (rules/game-over? winner-board))))

  (it "detects a game over due to a draw"
    (let [draw-board ["O" "O" "X"
                      "X" "X" "O"
                      "O" "X" "X"]]
      (should (rules/game-over? draw-board))))

  (it "detects a game over due to a full board"
    (let [full-board ["X" "O" "X"
                      "X" "O" "O"
                      "O" "X" "X"]]
      (should (rules/game-over? full-board))))

  (it "detects a game not over"
    (let [non-full-board ["X" "O" "X"
                          "X" "O" "O"
                          "O" "X" " "]]
      (should-not (rules/game-over? non-full-board))))
  )
