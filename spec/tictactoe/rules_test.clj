(ns tictactoe.rules-test
  (:require [speclj.core :refer :all]
            [tictactoe.rules :as rules]))
(describe "Game"
  (describe "tictactoe.game"
    (describe "get-winner"
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
          (should= "X" (rules/get-winner winner-board)))))
    )

  (describe "get next-player"
    (it "returns 'O' when given 'X'"
      (should= "O" (rules/get-next-player "X")))
    (it "returns 'X' when given 'O'"
      (should= "X" (rules/get-next-player "O"))))

  (describe "game-over?"
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
  )