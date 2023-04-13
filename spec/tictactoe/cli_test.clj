(ns tictactoe.cli-test
  (:require [speclj.core :refer :all]
            [tictactoe.cli :as cli]
            [clojure.string :as str]))

(defn with-out-str-replace [f & args]
  (let [out (java.io.StringWriter.)]
    (binding [*out* out]
      (apply f args)
      (str/replace (str (.toString out)) #"\r\n" "\n"))))

(describe "tictactoe.cli"
  (describe "print-message"
    (it "prints the given message"
      (should= "Hello, world!\n"
               (with-out-str (cli/print-message "Hello, world!")))))

  (describe "print-board"
    (it "prints the board"
      (let [board ["X" "O" "X"
                   "O" "X" "O"
                   " " " " "O"]]
        (should= (str "X | O | X\n"
                      "---------\n"
                      "O | X | O\n"
                      "---------\n"
                      "6 | 7 | O\n")
                 (with-out-str (cli/print-board board))))))

  (describe "get-input"
    (it "reads user input"
      (with-in-str "4\n"
                   (should= "4" (cli/get-input))))))
