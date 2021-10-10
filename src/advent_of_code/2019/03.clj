(ns advent-of-code.2019.03
  (:require
   [clojure.string :refer [split]]
   [advent-of-code.utils :refer [abs read-by-line]]))


(defn- trace-line [s]
  (let [xs (split s #",")]
    (loop [moves xs x 0 y 0 acc []]
      (let [value (first moves)
            [_ direction steps] (re-find #"(.)(\d+)" value)
            shift (read-string steps)
            [x' y'] (case direction
                      "U" [x (+' y shift)]
                      "D" [x (- y shift)]
                      "R" [(+' x shift) y]
                      "L" [(- x shift) y])
            acc' (conj acc [x' y'])]
        (if (empty? (rest moves))
          acc'
          (recur (rest moves) x' y' acc'))))))


(defn- manhattan-distance
  ([p]
   (manhattan-distance [0 0] p))
  ([[a b] [x y]]
   (when (and a b x y)
     (+ (abs (- a x))
        (abs (- b y))))))


(defn- intersections [xs]
  (let [fnx (fn [[x1 y1] [x2 y2]]
              (or (= x1 x2)
                  (= y1 y2)))
        a (first xs)
        b (second xs)
        result (into [] (for [la (range (count a))
                              lb (range (count b))]
                          (when (fnx (nth a la) (nth b lb))
                            [(nth a la) (nth b lb)])))]
    (filter some? result)))


(defn closest-intersection-distance [xs]
  (->> xs
       (map trace-line)
       intersections
       #_(map manhattan-distance)
       #_(apply min)))


(defn solve-1 [filename]
  (->> filename
       read-by-line
       closest-intersection-distance))


(comment
  (solve-1 "resources/inputs/2019/03.txt")
  )