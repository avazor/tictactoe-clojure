(ns tictactoe.game-test
  (:require [speclj.core :refer :all]
            [tictactoe.game :as game]
            [tictactoe.board :as board]))
(describe "Game"
  (describe "tictactoe.game"
    (describe "has-winner?"
      (it "detects a winning board"
        (let [winner-board ["X" "X" "X"
                            " " "O" " "
                            "O" " " " "]]
          (should= "X" (game/has-winner? winner-board))))

      (it "detects a non-winning board"
        (let [non-winner-board ["X" "O" "X"
                                "X" "O" "O"
                                "O" "X" " "]]
          (should= nil (game/has-winner? non-winner-board))))

      (it "detects a non-winning board"
        (let [non-winner-board ["O" "O" "X"
                                "X" "X" "O"
                                "O" "X" "X"]]
          (should= nil (game/has-winner? non-winner-board))))

      (it "detects a winning diagonal"
        (let [winner-board ["X" " " " "
                            " " "X" " "
                            " " " " "X"]]
          (should= "X" (game/has-winner? winner-board)))))
    )

  (describe "get opponent"
    (it "returns 'O' when given 'X'"
      (should= "O" (game/next-player "X")))
    (it "returns 'X' when given 'O'"
      (should= "X" (game/next-player "O"))))
  (describe "full?"
    (it "detects a full board"
      (let [full-board ["X" "O" "X"
                        "X" "O" "O"
                        "O" "X" "X"]]
        (should (board/full? full-board))))


    (it "detects a non-full board"
      (let [non-full-board ["X" "O" "X"
                            "X" "O" "O"
                            "O" "X" " "]]
        (should-not (board/full? non-full-board))))
    )
  (describe "game-over?"
    (it "detects a game over due to a win"
      (let [winner-board ["X" "X" "X"
                          " " "O" " "
                          "O" " " " "]]
        (should= "X" (game/game-over? winner-board))))

    (it "detects a game over due to a win"
      (let [winner-board ["X" "X" "X"
                          "O" "O" " "
                          " " " " " "]]
        (should= "X" (game/game-over? winner-board))))

    (it "detects a game over due to a draw"
      (let [draw-board ["O" "O" "X"
                        "X" "X" "O"
                        "O" "X" "X"]]
        (should (game/game-over? draw-board))))

    (it "detects a game over due to a full board"
      (let [full-board ["X" "O" "X"
                        "X" "O" "O"
                        "O" "X" "X"]]
        (should (game/game-over? full-board))))

    (it "detects a game not over"
      (let [non-full-board ["X" "O" "X"
                            "X" "O" "O"
                            "O" "X" " "]]
        (should-not (game/game-over? non-full-board))))
    )
  )