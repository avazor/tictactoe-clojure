(ns tictactoe.game.core-spec
  (:require [speclj.core :refer :all]
            [tictactoe.board :as board]
            [tictactoe.cli :as io]
            [tictactoe.db :as db]
            [tictactoe.game.core :as core]
            [tictactoe.rules :as rules])
  (:import (tictactoe.player.player PlayerStrategy)))

(defrecord TestHuman []
  PlayerStrategy
  (make-move [% % %] 4))

(defrecord TestEasyAI []
  PlayerStrategy
  (make-move [% % %] 4))
(describe "game core"
  (it "returns the next move based on the current player"
    (let [board (board/empty-board 9)
          player1 {:symbol "X" :player (->TestHuman)}
          player2 {:symbol "O" :player (->TestEasyAI)}]
      (should= 4 (core/next-move board "X" [player1 player2]))
      (should= 4 (core/next-move board "O" [player1 player2]))))

  (it "plays a turn and get winner"
    (let [game-id 1
          board (board/empty-board 9)
          player1 {:symbol "X" :player (->TestHuman)}
          player2 {:symbol "O" :player (->TestEasyAI)}]
      (with-redefs [io/print-board (fn [_] ())
                    core/next-move (fn [_ _ _] 4)
                    board/valid-move? (fn [_ _] true)
                    board/make-move (fn [board _ _] board)
                    rules/game-over? (fn [_] true)
                    rules/get-winner (fn [_] "X")
                    rules/get-next-player (fn [_] "O")
                    db/save-move (fn [& _] ())]
        (should= [board "X"] (core/play-turn game-id board "X" [player1 player2] 1)))))

  (it "plays a game and ensures that all functions are run in right order with correct args"
    (let [players [{:symbol "X" :player (->TestHuman) :type "HUMAN"}
                   {:symbol "O" :player (->TestEasyAI) :type "AI"}]
          board (board/empty-board 9)
          game-name "Test Game"
          play-turn-args (atom [])
          print-board-args (atom [])
          print-message-args (atom [])
          save-game-args (atom [])
          update-game-status-args (atom [])]
      (with-redefs [core/play-turn (fn [& args] (reset! play-turn-args args) [(board/empty-board 9) nil])
                    io/print-board (fn [arg] (swap! print-board-args conj arg))
                    io/print-message (fn [arg] (swap! print-message-args conj arg))
                    db/save-game (fn [& args] (reset! save-game-args args) 1)
                    db/update-game-status (fn [& args] (reset! update-game-status-args args))]
        (core/play-game players board game-name))
      (should= (first @play-turn-args) 1) ; game-id
      (should= (second @play-turn-args) board)
      (should= (nth @play-turn-args 2) "X") ; current-player
      (should= (nth @play-turn-args 3) players)
      (should= (nth @play-turn-args 4) 1) ; move-number
      (should= (first @print-board-args) board)
      (should= (first @print-message-args) "It's a draw!")
      (should= (first @save-game-args) game-name)
      (should= (nth @save-game-args 1) (:type (first players)))
      (should= (nth @save-game-args 2) (:type (second players)))
      (should= (nth @save-game-args 3) (board/board-size board))
      (should= (first @update-game-status-args) 1) ; game-id
      (should= (nth @update-game-status-args 1) true)))
  )
