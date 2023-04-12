(ns tictactoe.ai-test
  (:require [speclj.core :refer :all]
            [tictactoe.ai-player-hard :as ai]))

(describe "AI"
  (describe "get-score"
    (it "returns the correct score when the player is the winner"
      (should= 7 (ai/get-score "X" 3 "X"))
      (should= 5 (ai/get-score "O" 5 "O")))

    (it "returns the correct score when the player is not the winner"
      (should= -7 (ai/get-score "O" 3 "X"))
      (should= -5 (ai/get-score "X" 5 "O")))

    (it "returns 0 when there's no winner"
      (should= 0 (ai/get-score nil 3 "X"))
      (should= 0 (ai/get-score nil 5 "O")))
    )

  (describe "minimax"
    (it "returns the correct score for a non-terminal game state"
      (let [board ["O" " " "X"
                   "X" "X" "O"
                   "O" " " "X"]
            depth 3
            player "X"
            ai-player "O"]
        (should= 0 (ai/minimax board depth player ai-player))))
    )
  (describe "ai moves"
    (it "returns 3 as best move"
      (let [board ["O" " " "X"
                   " " "X" "X"
                   "O" " " "O"]]
        (should= 3 (ai/minimax-move board "X"))))

    (it "returns 7 as best move"
      (let [board ["O" "X" " "
                   " " "O" " "
                   "X" " " "X"]]
        (should= 7 (ai/minimax-move board "O"))))

    (it "returns 4 as best move"
      (let [board ["O" "X" " "
                   " " " " " "
                   "X" " " " "]]
        (should= 4 (ai/minimax-move board "O"))))

    (it "returns 2 as best move"
      (let [board ["O" "O" " "
                   " " "X" " "
                   "X" " " "X"]]
        (should= 2 (ai/minimax-move board "O"))))

    (it "returns 8 as best move"
      (let [board ["O" " " " "
                   " " " " "X"
                   " " "X" " "]]
        (should= 8 (ai/minimax-move board "O"))))
    )
  )


