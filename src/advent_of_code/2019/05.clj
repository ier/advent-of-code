(ns advent-of-code.2019.05
  (:require
   [clojure.string :refer [split trim]]))

(defn parse [filename]
  (-> filename
      slurp
      trim
      (split #",")))



(defn solve-1 [filename]
  (-> filename
      parse))

(comment
  (solve-1 "resources/inputs/2019/05.txt") )
