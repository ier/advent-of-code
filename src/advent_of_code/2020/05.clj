(ns advent-of-code.2020.05
  (:require
   [advent-of-code.utils :as utils]))


(defn- update-interval
  [[start end] s]
  (cond
    (contains? #{"F" "L"} s) [start (- (/ (+ 1 start end) 2) 1)]
    (contains? #{"B" "R"} s) [(/ (+ 1 start end) 2) end]))


(defn- decode-row [row]
  (loop [pos 0
         interval [0 127]]
    (let [s (subs row pos (inc pos))
          interval* (update-interval interval s)]
      (if (= pos 6)
        (first interval*)
        (recur (inc pos) interval*)))))


(defn- decode-col [col]
  (loop [pos 0
         interval [0 7]]
    (let [s (subs col pos (inc pos))
          interval* (update-interval interval s)]
      (if (= pos 2)
        (first interval*)
        (recur (inc pos) interval*)))))


(defn- decode [s]
  (let [row-part (subs s 0 7)
        col-part (subs s 7 10)
        row (decode-row row-part)
        col (decode-col col-part)
        res (+ (* row 8) col)]
    res))


(defn- get-skipped-number [decoded]
  (let [sorted (sort decoded)]
    (loop [expected (first sorted)
           items sorted]
      (if (nil? (first items))
        "Not found"
        (if (not= expected (first items))
          (dec (first items))
          (recur (inc expected) (rest items)))))))


(defn solve []
  (let [lines (utils/->vec-of-str "resources/inputs/2020/05.txt")
        decoded (map decode lines)
        maximal (apply max decoded)
        free-seat-id (get-skipped-number decoded)]
    (str "Part 1: " maximal "; Part 2: " free-seat-id)))


(comment
  (solve)
  )
