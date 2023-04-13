(ns tictactoe.ai-test
  (:require [speclj.core :refer :all]
            [tictactoe.ai-player-hard :refer :all]))

(describe "AI"
  (describe "#game-evaluation"
    (it "returns the correct score when the player is the winner"
      (should= 10 (game-evaluation "X" "X"))
      (should= 10 (game-evaluation "O" "O")))

    (it "returns the correct score when the player is not the winner"
      (should= -10 (game-evaluation "O" "X"))
      (should= -10 (game-evaluation "X" "O")))

    (it "returns 0 when there's no winner"
      (should= 0 (game-evaluation false "X"))
      (should= 0 (game-evaluation false "O")))
    )

  (describe "#minimax"
    (it "returns 0 if board is full with out a winner"
      (should= 0
               (minimax ["X" "O" "X"
                         "O" "O" "X"
                         "X" "X" "O"] "O" "O")))
    (it "returns 10 if has winner and current player is human"
      (should= 10
               (minimax ["X" "X" "X"
                         "O" "O" " "
                         " " " " " "] "X" "X")))
    (it "returns 10 if has winner and curent player is computer"
      (should= 10
               (minimax ["O" "O" "O"
                         "X" "X" " "
                         " " " " " "] "O" "O")))
    (it "returns an eval of 2 for a game you can't lose"
      (should= 10
               (minimax [" " "X" " "
                         " " "O" " "
                         " " "X" " "] "O" "X")))
    (it "returns an eval of 10 for a maximum tied game"
      (should= 0
               (minimax ["X" " " " " " " " " " " " " " " " "] "O" "X"
                        )))
    (it "returns an eval of 0 for a game bound to lose"
      (should= -10
               (minimax ["X" "O" " " " " "X" " " " " " " " "] "O" "X"
                        ))))

  (describe "#get-scores"
    (it "getting scores for available moves"
      (should= [-10 0 0 -10 0 -10]
               (get-scores ["O" "X" " "
                            "X" " " " "
                            " " " " " "] "O" "O"))))

  (describe "#minimax-move"
    (it "returns the best move for a winning position"
      (should= 2
               (minimax-move ["O" "X" " "
                              "X" "O" " "
                              " " " " " "] "O"))
      (should= 0
               (minimax-move [" " "X" "X"
                              "O" "O" " "
                              " " " " " "] "X")))

    (it "returns the best move for a defensive position"
      (should= 8
               (minimax-move ["O" "X" " "
                              "X" "O" " "
                              " " " " " "] "X"))
      (should= 1
               (minimax-move ["X" " " "X"
                              "X" "O" "O"
                              " " " " " "] "O"))))
  )
