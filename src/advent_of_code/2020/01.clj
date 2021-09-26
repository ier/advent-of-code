(ns advent-of-code.2020.01
  (:require
   [advent-of-code.utils :as utils]))


(defn distinct-by
  [f coll]
  (let [groups (group-by f coll)]
    (map #(first (groups %)) (distinct (map f coll)))))


(defn solve []
  (let [data (utils/->vec-of-str "resources/inputs/2020/01.txt")
        v (into [] (map #(Integer/parseInt %) data))
        result1 (let [xs (for [x1 v
                               x2 v]
                           {:sum (+ x1 x2) :res (* x1 x2)})
                      filtered (filter #(= 2020 (:sum %)) xs)]
                  (distinct-by :sum filtered))
        result2 (let [xs (for [x1 v
                               x2 v
                               x3 v]
                           {:sum (+ x1 x2 x3) :res (* x1 x2 x3)})
                      filtered (filter #(= 2020 (:sum %)) xs)]
                  (distinct-by :sum filtered))]
    [result1 result2]))


(comment
  (solve)
  )
