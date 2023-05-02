(defproject tictactoe "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main tictactoe.core
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/java.jdbc "0.7.12"]
                 [mysql/mysql-connector-java "8.0.27"]
                 [com.h2database/h2 "2.1.214"]]
  :profiles {:dev {:dependencies [[speclj "3.4.3"]]}}
  :plugins [[speclj "3.4.2"]]
  :test-paths ["spec"])
