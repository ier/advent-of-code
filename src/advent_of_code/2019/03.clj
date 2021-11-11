(ns advent-of-code.2019.03
  (:require
   [clojure.string :refer [split]]
   [advent-of-code.utils :refer [abs read-by-line]]))


(defn- trace-line [s]
  (let [xs (split s #",")]
    (loop [moves xs x 0 y 0 acc [[0 0]]]
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


(defn- cross-point
  [[ax ay] [bx by] [cx cy] [dx dy]]
  (cond
    (or (and (< ax cx bx) (< cy ay dy))
        (and (< ax cx bx) (> cy ay dy))
        (and (> ax cx bx) (< cy ay dy))
        (and (> ax cx bx) (> cy ay dy))) [cx ay]
    (or (and (< cx ax dx) (< by cy ay))
        (and (< cx ax dx) (> by cy ay))
        (and (> cx ax dx) (< by cy ay))
        (and (> cx ax dx) (> by cy ay))) [ax cy]
    (and (= ax cx) (= ay cy)
         (= bx cx) (= by cy)) [cx cy]
    (and (= ax dx) (= ay dy)
         (= bx dx) (= by dy)) [dx dy]
    (or (and (= ax bx cx) (< ay cy by))
        (and (= ax bx cx) (> ay cy by))) [cx cy]
    (or (and (= ax bx dx) (< ay dy by))
        (and (= ax bx dx) (> ay dy by))) [dx dy]))


(defn- intersections [xs]
  (let [a (first xs)
        b (second xs)
        result (into
                []
                (for [la (range (dec (count a)))
                      lb (range (dec (count b)))]
                  (let [point (cross-point
                               (nth a la)
                               (nth a (inc la))
                               (nth b lb)
                               (nth b (inc lb)))]
                    (when (some? point)
                      point))))]
    (filter some? result)))


(defn closest-intersection-distance [xs]
  (->> xs
       (map trace-line)
       intersections
       (map manhattan-distance)
       (apply min)))


(defn solve-1 [filename]
  (->> filename
       read-by-line
       closest-intersection-distance))


(comment
  (solve-1 "resources/inputs/2019/03.txt"))


(defn- dff
  [[p0x p0y] [p1x p1y] [p2x p2y]]
  (cond
    (and (= p0x p1x p2x)
         (or (< p1y p0y p2y)
             (> p1y p0y p2y))) (abs (- p1y p0y))
    (and (= p0y p1y p2y)
         (or (< p1x p0x p2x)
             (> p1x p0x p2x))) (abs (- p1x p0x))))


(defn length
  [xs point]
  (loop [turns xs cnt 0 acc 0]
    (let [p1 (first turns)
          p2 (second turns)
          diff (dff point p1 p2)]
      (if (= cnt (count xs))
        (+ acc diff)
        (recur (next (drop cnt turns)) (inc cnt) (+ acc diff))))))


(defn fewest-combined-steps [xs]
  (let [traces (map trace-line xs)
        points (intersections traces)
        path-pairs-to-point (map #(length traces %) points)]
    path-pairs-to-point))


(defn solve-2 [filename]
  (->> filename
       read-by-line
       fewest-combined-steps))


(comment
  (solve-2 "resources/inputs/2019/03.txt"))
