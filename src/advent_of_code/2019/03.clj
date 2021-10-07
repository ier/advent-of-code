(ns advent-of-code.2019.03
  (:require
   [clojure.java.io :as io]
   [clojure.string :refer [split]]))


(defn- read-by-line [filename]
  (with-open [rdr (io/reader filename)]
    (reduce conj [] (line-seq rdr))))


(defn- trace-position [xs]
  (loop [moves xs x 0 y 0]
    (let [[_ direction steps] (re-find #"(.)(\d+)" (first moves))
          shift (read-string steps)
          [dx dy] (case direction
                    "U" [x (+ y shift)]
                    "D" [x (- y shift)]
                    "R" [(+ x shift) y]
                    "L" [(- x shift) y])
          _ (prn [dx dy])]
      (if (empty? steps)
        [x y]
        (recur (rest moves) (+ x dx) (+ y dy))))))


(defn- parse-line [s]
  (->> (split s #",")
       trace-position))


(defn solve-1 [filename]
  (->> filename
       read-by-line
       (map parse-line)))


(comment
  (solve-1 "resources/inputs/2019/03.txt"))