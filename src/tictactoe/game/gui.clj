(ns tictactoe.game.gui
  (:require
    [seesaw.core]
    [seesaw.core :as seesaw]
    [seesaw.mig :as mig]
    [tictactoe.board :as board]
    [seesaw.bind :as b])
  (:use [seesaw.core]))
;
;(defn select-player-type []
;  (let [player-options ["human" "easyAI" "mediumAI" "hardAI"]
;        player-type-atom (atom nil)]
;    (seesaw/invoke-later
;      (let [frame (seesaw/frame :title "Select Player Type" :width 300 :height 200 :on-close :exit)
;            button-group (seesaw/button-group)
;            radio-buttons (map (fn [player-option]
;                                 (let [radio-button
;                                       (seesaw/radio
;                                         :text player-option
;                                         :listen [:action (fn [_] (reset! player-type-atom player-option))])]
;                                   (seesaw.core/add! button-group radio-button)
;                                   radio-button))
;                               player-options)
;            panel (seesaw/vertical-panel
;                    :items radio-buttons
;                    :border 10)]
;        (seesaw/config! frame :content panel)
;        (seesaw/pack! frame)
;        (seesaw/show! frame)))
;    (while (nil? @player-type-atom)
;      (Thread/sleep 500))
;    @player-type-atom))
;
;
;(defn select-board-size []
;  (let [size-options [["3x3" 3] ["4x4" 4]]
;        board-size-atom (atom nil)]
;    (seesaw/invoke-later
;      (let [frame (seesaw/frame :title "Select Board Size" :width 300 :height 200 :on-close :exit)]
;        (seesaw/config! frame
;                        :content
;                        (seesaw/box-panel
;                          (apply seesaw/button-group
;                                 (map (fn [[lbl size]] (seesaw/radio :text lbl :value size)) size-options))
;                          (seesaw/button :text "Select"
;                                         :listen [:action (fn [_] (reset! board-size-atom
;                                                                          (seesaw/selection (seesaw.core/selection frame)))
;                                                            (seesaw/dispose! frame))])))
;        (seesaw/show! frame)))
;    (while (nil? @board-size-atom) (Thread/sleep 100))
;    @board-size-atom))
;
;(defn valid-move? [game-state row col]
;  (and (>= row 0) (< row (count game-state))
;       (>= col 0) (< col (count (first game-state)))
;       (= (get-in game-state [row col]) :empty)))
;
;(defn make-move [game-state player row col]
;  (if (valid-move? game-state row col)
;    (assoc-in game-state [row col] player)
;    game-state))
;
;(defn start-game [player-types board-size game-name]
;  (let [game-state (atom (board/empty-board board-size))]
;    (seesaw/invoke-later
;      (let [frame (seesaw/frame :title game-name :width 400 :height 400 :on-close :exit)
;            status-label (seesaw/label :text "Player 1's turn")
;            board-grid (seesaw/grid-panel :rows board-size :columns board-size :hgap 10 :vgap 10)]
;        (dotimes [row board-size]
;          (dotimes [col board-size]
;            (let [cell (seesaw/button :text "")]
;              (seesaw/listen cell :action (fn [_]
;                                            (make-move @game-state :player1 row col)
;                                            (seesaw/config! cell :text "X")
;                                            (seesaw/config! status-label :text "Player 2's turn")))
;              (seesaw.core/add! board-grid cell))))
;        (seesaw/config! frame :content (seesaw/box-panel status-label board-grid))
;        (seesaw/show! frame)))))
;
;
;(defn create-new-game []
;  (seesaw/invoke-later
;    (let [frame (seesaw/frame :title "Tic Tac Toe" :width 400 :height 400 :on-close :exit)]
;      (seesaw/show! frame)))
;  (let [player-types (select-player-type)
;        board-size (select-board-size)
;        game-name (seesaw/value (seesaw/text :hint "Enter game name"))]
;    (seesaw/invoke-later (start-game player-types board-size game-name))))
;
;

(defn- game-form []
  (let [panel (grid-panel
                :border [10 "Here's a grid layout with 3 columns" 10]
                :hgap 10
                :vgap 10
                :columns 3
                :items (map #(action
                               :handler (fn [e] (alert (str "Clicked " %)))
                               :name " ")
                            (range 0 9)))]
  (seesaw/frame :title "TicTacToe Game", :on-close :exit :size [400 :by 400], :content panel)))

(defn create-new-game []
  (seesaw/show!(game-form)))