(ns tictactoe.player.hard
  (:require [tictactoe.board :as board]
            [tictactoe.rules :as rules]
            [tictactoe.player.player :as strategy])
  )

(def search-start-time (volatile! 0))
(def search-time-limit 5000)

(defn time-exceeded? []
  (> (- (System/currentTimeMillis) @search-start-time) search-time-limit))

(defn minimax [board depth alpha beta maximizing-player ai-player]
  (if (or (rules/game-over? board) (time-exceeded?))
    (let [winner (rules/get-winner board)]
      (cond
        (= winner ai-player) {:score 10}
        (= winner (rules/get-next-player ai-player)) {:score -10}
        :else {:score 0}))
    (if maximizing-player
      (let [best-move {:score -1000}
            remaining-moves (filter #(= " " (board/get-cell board %)) (range (count board)))]
        (loop [moves remaining-moves alpha alpha best-move best-move]
          (if (seq moves)
            (let [move (first moves)
                  board-with-move (board/make-move board move ai-player)
                  score (minimax board-with-move (dec depth) alpha beta false ai-player)]
              (if (> (:score score) (:score best-move))
                (recur (rest moves) (max alpha (:score score)) {:score (:score score) :move move})
                (recur (rest moves) alpha best-move)))
            best-move)))
      (let [best-move {:score 1000}
            remaining-moves (filter #(= " " (board/get-cell board %)) (range (count board)))]
        (loop [moves remaining-moves beta beta best-move best-move]
          (if (seq moves)
            (let [move (first moves)
                  board-with-move (board/make-move board move (rules/get-next-player ai-player))
                  score (minimax board-with-move (dec depth) alpha beta true ai-player)]
              (if (< (:score score) (:score best-move))
                (recur (rest moves) (min beta (:score score)) {:score (:score score) :move move})
                (recur (rest moves) beta best-move)))
            best-move))))))

(defn move-score [board index player]
  (let [winning-lines (rules/get-winning-positions (board/board-size board))
        potential-lines (filter #(some #{index} %) winning-lines)
        opponent (tictactoe.rules/get-next-player player)]
    (reduce
      (fn [score line]
        (let [player-count (count (filter #(= player %) (map #(get board %) line)))
              opponent-count (count (filter #(= opponent %) (map #(get board %) line)))]
          (cond
            (= player-count (dec (count line))) (+ score 10)
            (= opponent-count (dec (count line))) (+ score 5)
            :else score)))
      0 potential-lines)))

(defn minimax-move [board ai-player]
  (let [available-moves (filter #(= " " (board/get-cell board %)) (range (count board)))
        max-depth 15
        scored-moves (map (fn [index] {:index index :score (move-score board index ai-player)}) available-moves)
        ordered-moves (map :index (sort-by :score > scored-moves))]
    (vreset! search-start-time (System/currentTimeMillis))
    (loop [depth 1
           best-move {:index (first ordered-moves) :score -100}]
      (if (< depth max-depth)
        (let [result (->> ordered-moves
                          (map (fn [index]
                                 (let [board-with-move (board/make-move board index ai-player)
                                       score (minimax board-with-move depth -100 100 false ai-player)]
                                   {:index index :score (:score score)})))
                          (reduce (fn [best-move current-move]
                                    (if (> (:score current-move) (:score best-move))
                                      current-move
                                      best-move))
                                  {:index (first ordered-moves) :score -100}))]
          (if (time-exceeded?) (:index best-move) (recur (inc depth) result)))
        (:index best-move)))))

(defrecord HardAI []
  strategy/PlayerStrategy
  (make-move [this board symbol]
    (minimax-move board symbol)))