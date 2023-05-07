(ns tictactoe.player.hard-spec
  (:require [speclj.core :refer :all]
            [tictactoe.board :as board]
            [tictactoe.player.hard :as hard-ai]))

(describe "HardAI"
  (tags :slow)
  (context "3x3"
      (it "AI makes a winning move"
        (let [board ["X" " " " "
                     "O" "O" " "
                     "X" "X" " "]
              ]
          (should= 5 (hard-ai/minimax-move board "O"))))
      (it "AI blocks opponent from winning"
        (let [current-board ["X" " " " "
                             "O" "O" " "
                             "X" " " " "]]
          (should= 5 (hard-ai/minimax-move current-board "X"))))
      (it "AI selects a valid move"
        (let [current-board ["X" " " " "
                             "O" " " " "
                             " " " " " "]
              ai-player "X"
              move (hard-ai/minimax-move current-board ai-player)]
          (should= " " (board/get-cell current-board move)))

        (let [current-board ["O" "X" "O"
                             "X" "O" "X"
                             "X" "O" " "]
              ai-player "O"]
          (should= (hard-ai/minimax-move current-board ai-player) 8))))
    (context "4x4"
      (it "AI blocks opponent from winning"
        (let [board [ " " "X" " " " "
                      " " "X" " " " "
                      " " "X" " " "O"
                      " " " " " " " " ]
              ]
          (should= 13 (hard-ai/minimax-move board "O"))))

      (it "AI makes a winning move"
        (let [board [" " "X" " " "O"
                     " " "X" " " "O"
                     " " "X" " " "O"
                     " " " " " " " " ]
              ]
          (should= 15 (hard-ai/minimax-move board "O"))))
      )
  (context "3x3x3"
    (it "AI blocks opponent from winning"

      (let [board ["O" " " " "
                   " " " " " "
                   " " "X" " "

                   " " " " " "
                   " " " " " "
                   " " "X" " "

                   " " "O" " "
                   " " " " " "
                   " " " " " "]
            ]
        (should= 25 (hard-ai/minimax-move board "O"))))

    (it "AI makes a winning move"
      (let [board ["O" " " "O"
                   " " " " " "
                   " " "X" " "

                   " " " " " "
                   " " " " " "
                   " " "X" " "

                   " " "O" " "
                   " " " " " "
                   " " " " " "]]
        (should= 1 (hard-ai/minimax-move board "O")))))
    )

