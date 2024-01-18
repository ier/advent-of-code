(ns advent-of-code.2019.03
  (:require
   [clojure.string :refer [split]]
   [advent-of-code.utils :refer [read-by-line]]
   [advent-of-code.core :refer [plot]]))

(defn- trace-line
  [s]
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
     (+ (Math/abs (- a x))
        (Math/abs (- b y))))))

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

(defn- intersections
  [xs]
  (let [[a b & _] xs
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

(defn closest-intersection-distance
  [xs]
  (->> xs
       (map trace-line)
       intersections
       (map manhattan-distance)
       (apply min)))

(defn solve-1
  [filename]
  (->> filename
       read-by-line
       closest-intersection-distance))

(comment
  (solve-1 "resources/inputs/2019/03.txt")
  )

(defn- get-ln
  [[px py] [sx sy] [ex ey]]
  (cond
    (and (= px sx ex)
         (or (< sy py ey)
             (> sy py ey))) (Math/abs (- py sy))
    (and (= py sy ey)
         (or (< sx px ex)
             (> sx px ex))) (Math/abs (- px sx))
    :else (+ (Math/abs (- sx ex)) (Math/abs (- sy ey)))))

(defn- intersected?
  [[px py] [sx sy] [ex ey]]
  (or (= px sx ex)
      (= py sy ey)))

(defn- len
  [[xs point]]
  (loop [turns xs
         acc 0]
    (if (= 1 (count turns))
      acc
      (let [[p1 p2 & _] turns
            ln (get-ln point p1 p2)]
        (if (intersected? point p1 p2)
          (+ acc ln)
          (recur (next turns)
                 (+ acc ln)))))))

(defn- populate
  [traces points]
  (map (fn [p]
         (map (fn [t]
                (conj [t] p)) traces))
       points))

(defn- calculate
  [xs]
  (let [traces (map trace-line xs)
        points (intersections traces)]
    {:traces traces
     :points points}))

(defn fewest-combined-steps
  [xs]
  (let [{:keys [traces points]} (calculate xs)]
    (->> (populate traces points)
         (apply concat)
         (map len)
         (partition 2)
         (map #(apply + %))
         (apply min))))

(defn solve-2
  [filename]
  (->> filename
       read-by-line
       fewest-combined-steps))

(comment
  (solve-2 "resources/inputs/2019/03.txt")

  (let [lines (->> "resources/inputs/2019/03.txt"
                   read-by-line
                   (map trace-line)
                   (map (fn [line]
                          (map (fn [[x y]] (hash-map :x x :y y)) line))))]
    (clojure.set/intersection (set (first lines)) (set (last lines))))


(let [v [[10 1] [11 2] [12 3]]]
  (map (fn [[x y]] (hash-map :x x :y y)) v))

(let [v (list (list {:x 1 :y 0} {:x 2 :y 2}) (list {:x 1 :y 1} {:x 2 :y 2}))]
  (clojure.set/intersection (set (first v)) (set (last v))))

  (let [data (read-by-line "resources/inputs/2019/03.txt")
        #_#_#_#_#_#_
        data ["R75,D30,R83,U83,L12,D49,R71,U7,L72"
              "U62,R66,U55,R34,D71,R55,D58,R83"]
        data ["U7,R6,D3,L2,U3,R2,D4,L4"
              "R8,U5,L5,D3"]
        data ["U6,L2,D2,R2,U3,R6,D4,L4"
              "R8,U5,L5,D3"]
        {:keys [traces points]} (calculate data)
        min-max (fn [traces]
                  (let [items (flatten traces)]
                    [(apply min items) (apply max items)]))
       [min max] (min-max traces)
        padding 5]
    (plot {:title "Simple test"
           :rows-data (conj (vec traces) (vec points) (first traces))
           :rows-titles ["a" "b" "x" "d1"]
           :with [:lines :lines :points :points]
           :range {:min (- min padding) :max (+ max padding)}}))
  )
