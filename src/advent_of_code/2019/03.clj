(ns advent-of-code.2019.03
  (:require
   [clojure.string :refer [split]]
   [advent-of-code.utils :refer [abs read-by-line]]
   [gnuplot.core :as gpc]))

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

(defn- intersections
  [xs]
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

(defn- dff
  [[p0x p0y] [p1x p1y] [p2x p2y]]
  (let [result (cond
                 (and (= p0x p1x p2x)
                      (or (<= p1y p0y p2y)
                          (>= p1y p0y p2y))) (- p1y p0y)
                 (and (= p0y p1y p2y)
                      (or (<= p1x p0x p2x)
                          (>= p1x p0x p2x))) (- p1x p0x))]
    {:in [[p0x p0y] [p1x p1y] [p2x p2y]]
     :res result}))

(defn- get-ln
  [[px py] [sx sy] [ex ey]]
  (cond
    (and (= px sx ex)
         (or (<= sy py ey)
             (>= sy py ey))) (abs (- py sy))
    (and (= py sy ey)
         (or (<= sx px ex)
             (>= sx px ex))) (abs (- px sx))
    :else (+ (abs (- sx ex)) (abs (- sy ey)))))

(comment
  {:traces '([[0 0] [0 7] [6 7] [6 3] [2 3]] [[0 0] [8 0] [8 5] [3 5] [3 2]]),
   :points '([6 5] [3 3]),
   :dbg '(19 20)}
  )

(defn- len
  [[xs point]]
  (loop [turns xs
         acc 0]
    (if (= 1 (count turns))
      acc
      (let [p1 (first turns)
            p2 (second turns)
            ln (get-ln point p1 p2)]

        (recur (next turns)
               (+ acc ln))))))

(defn fewest-combined-steps
  [xs]
  (let [traces (map trace-line xs)
        points (intersections traces)
        pairs (->> (interleave traces points)
                   (partition 2)
                   (map len))]
    {:traces traces
     :points points
     :dbg pairs}))

(comment
  ;; 8+5+5+2 = 20
  ;; 7+6+4+3 = 20
  ;; 20+20 = 40
  ;; ...
  ;; 8+5+2 = 15
  ;; 7+6+2 = 15
  ;; 15+15 = 30
  (fewest-combined-steps
   ["U7,R6,D4,L4"
    "R8,U5,L5,D3"])
  )

(comment
  (let [{:keys [traces points result]}
        (fewest-combined-steps
         ["R75,D30,R83,U83,L12,D49,R71,U7,L72"
          "U62,R66,U55,R34,D71,R55,D58,R83"])
       lines (vec traces)
       dots (vec points)]
   (gpc/raw-plot!
    [[:set :title "simple-test"]
     [:plot (gpc/range -50 300)
      (gpc/list ["-" :title "a" :with :lines]
                ["-" :title "b" :with :lines]
                ["-" :title "x" :with :points])]]
    (conj lines dots)))
  )


(comment
  ;; 8+5+5+2 = 20
  ;; 7+6+4+3 = 20
  ;; 20+20 = 40
  ;; ...
  ;; 8+5+2 = 15
  ;; 7+6+2 = 15
  ;; 15+15 = 30
  (fewest-combined-steps
   ["U7,R6,D4,L4"
    "R8,U5,L5,D3"])
  )


(defn plot
  "https://github.com/aphyr/gnuplot/issues/6"
  []
  (gpc/raw-plot!
   [[:set :title "simple-test"]
    [:plot (gpc/range 0 5)
     (gpc/list
      ["-" :title "rising" :with :lines]
      ["-" :title "falling" :with :impulse])]]
   [[[0 0]
     [1 1]
     [2 2]
     [3 1]
     [4 3]
     [5 4]]
    [[0 5]
     [1 4]
     [2 3]
     [3 2]
     [4 1]
     [5 0]]]))

(comment
  (plot)
  )

(defn solve-2
  [filename]
  (->> filename
       read-by-line
       fewest-combined-steps))

(comment
  (solve-2 "resources/inputs/2019/03.txt")
  )
