(ns advent-of-code.2019.03
  (:require
   [clojure.java.io :as io]
   [clojure.string :refer [split]]
   [advent-of-code.utils :refer [abs]]))


(defn- read-by-line [filename]
  (with-open [rdr (io/reader filename)]
    (reduce conj [] (line-seq rdr))))


(defn- trace-line [s]
  (let [xs (split s #",")]
    (loop [moves xs x 0 y 0]
      (let [value (first moves)
            [_ direction steps] (re-find #"(.)(\d+)" value)
            shift (read-string steps)
            [dx dy] (case direction
                      "U" [x (+' y shift)]
                      "D" [x (- y shift)]
                      "R" [(+' x shift) y]
                      "L" [(- x shift) y])
            x' (+' x dx)
            y' (+' y dy)]
        (if (empty? (rest moves))
          [x' y']
          (recur (rest moves) x' y'))))))


(defn- manhattan-distance
  [[x y]]
  (+ (abs (- 0 x))
     (abs (- 0 y))))


(defn- intersections [xs]
  (let [fnx (fn [[x1 y1] [x2 y2]]
              (and (= x1 x2)
                   (= y1 y2)))
        a (first xs)
        b (second xs)
        result (atom '())
        size-a (count a)
        size-b (count b)]
    (for [la (range size-a)
          lb (range size-b)]
      (when (fnx (nth a la) (nth b lb))
        (swap! result conj (nth a la))))
    @result))


(defn closest-intersection-distance [lines]
  (->> lines
       (map trace-line)
       intersections
       (map manhattan-distance)
       (apply min)))


(defn solve-1 [filename]
  (->> filename
       read-by-line
       closest-intersection-distance))


(comment
  (solve-1 "resources/inputs/2019/03.txt")
  )