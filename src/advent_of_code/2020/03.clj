(ns advent-of-code.2020.03
  (:require
   [advent-of-code.utils :refer [->vec-of-str]]))


(defn travel
  [dx dy file-name]
  (let [lines (->vec-of-str file-name)
        max-x (count (first lines))
        max-y (count lines)]
    (loop [x 0 y 0 cnt 0]
      (if (< y max-y)
        (let [line (get lines y)
              pos (mod x max-x)
              value (subs line pos (inc pos))
              cnt' (if (= value "#") (inc cnt) cnt)]
          (recur (+ pos dx) (+ y dy) cnt'))
        cnt))))


(defn solve [file-name]
  (let [result1 (travel 3 1 file-name)
        xs [[1 1] [3 1] [5 1] [7 1] [1 2]]
        result2 (reduce * (map #(travel (first %) (second %) file-name) xs))]
    [result1 result2]))


(comment
  (solve "resources/inputs/2020/03.txt")
  )
