(ns tictactoe.core-test
  (:require [speclj.core :refer :all]
            [tictactoe.core :as core]
            [tictactoe.board :as board]
            ))

(describe "next-move"
  (it "returns the human player's move when it's their turn"
    (with-redefs [core/human-move (fn [] 4)]
      (should= (core/next-move ["X" "O" "X" "O" " " "O" " " " " " "] "X" "X") 4))
    (it "returns the AI's move when it's the AI's turn"
      (with-redefs [core/ai-move (fn [board player] 6)]
        (should= (core/next-move ["X" "O" "X" "O" "X" "O" " " " " " "] "O" "X") 6)))
    ))

(describe "play-turn"
  (it "plays a full game with a winner"
    (with-redefs [core/next-move (fn [board player player1]
                                   (let [moves {"X" [0 1 2], "O" [3 4 5]}]
                                     (first (drop (count (filter #(= player %) board)) (get moves player)))))]

      (should= (core/play-turn (board/empty-board) "X" "X" "O")
               [["X" "X" "X" "O" "O" "O" " " " " " "] "X"])))

  (it "plays a full game with a draw"
    (with-redefs [core/next-move (fn [board player player1]
                                   (let [moves {"X" [0 2 3 5 6], "O" [1 4 7 8]}]
                                     (first (drop (count (filter #(= player %) board)) (get moves player)))))]

      (should= (core/play-turn (board/empty-board) "X" "X" "O")
               [["X" "O" "X" "X" "O" "X" "X" "O" "O"] nil]))))

;(describe "play-game"
;  (it "plays the game until it's over"
;    (let [mock-human-moves (atom [0 1 2])
;          mock-ai-moves (atom [4 5])]
;      (with-redefs [core/human-move (fn [board player] (first (swap! mock-human-moves rest)))
;                    core/ai-move (fn [board player] (first (swap! mock-ai-moves rest)))]
;        (let [player1 "X"
;              player2 "O"
;              starting-player player1
;              initial-board (board/empty-board)
;              [final-board winner game-over?] (core/play-game initial-board starting-player player1 player2)]
;          (should= game-over? true)
;          (should= winner "X")
;          (should= (game/has-winner? final-board) true))))))


#_(focus-describe "Core"

  (it "foo"
    (let [output (with-out-str (t/foo))]
      (should= "foo\n" output)))

  (it "bar"
    (let [input (with-in-str "bar\n" (t/bar))]
      (should= "bar" input)))

  )