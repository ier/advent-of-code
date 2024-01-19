(ns advent-of-code.2019.03
  (:require
   [clojure.string :refer [split]]
   [advent-of-code.utils :refer [read-by-line]]
   [clojure.edn :refer [read-string]]
   [clojure.set :refer [intersection]]))

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

(defn solve-2
  [filename]
  (let [line-points (fn [start-point [dir steps]]
                      (let [step-line (case dir
                                        "U" #(update start-point :y + %)
                                        "D" #(update start-point :y - %)
                                        "R" #(update start-point :x + %)
                                        "L" #(update start-point :x - %))]
                        (->> steps
                             inc
                             (range 1)
                             (map step-line))))
        [grid-1 grid-2] (->> filename
                             read-by-line
                             (map (fn [s]
                                    (->> (split s #",")
                                         (map (fn [s]
                                                [(subs s 0 1)
                                                 (read-string (subs s 1))])))))
                             (map (fn [wire]
                                    (reduce (fn [grid line]
                                              (into grid (line-points (last grid) line)))
                                            [{:x 0 :y 0}]
                                            wire))))]
    (->> (intersection (set grid-1) (set grid-2))
         (map #(+ (.indexOf grid-1 %) (.indexOf grid-2 %)))
         (filter #(not (zero? %)))
         (apply min))))

(comment
  (solve-2 "resources/inputs/2019/03.txt")
  )

(comment
  (let [data (read-by-line "resources/inputs/2019/03.txt")
        {:keys [traces points]} nil ;; data processing
        min-max (fn [traces]
                  (let [items (flatten traces)]
                    [(apply min items) (apply max items)]))
        [min max] (min-max traces)
        padding 5]
    (advent-of-code.core/plot {:title "Simple test"
                               :rows-data (conj (vec traces) (vec points) (first traces))
                               :rows-titles ["a" "b" "x" "d1"]
                               :with [:lines :lines :points :points]
                               :range {:min (- min padding) :max (+ max padding)}}))
  )
