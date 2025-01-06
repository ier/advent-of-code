(ns advent-of-code.2019.04
  (:require
   [clojure.string :refer [split]]
   [advent-of-code.utils :refer [read-by-line digits]]))

(defn parse [s]
  (->> (split s #"-")
       (map #(Integer/parseInt %))))

(defn has-eq-items-seq? [xs]
  (loop [ints xs]
    (if (> (count ints) 1)
      (let [[f s] ints]
        (if (= f s)
          true
          (recur (next ints))))
      false)))

(defn check [x]
  (let [nums (digits x)]
    (and
     (apply <= nums)
     (has-eq-items-seq? nums))))

(defn calc
  [[start end]]
  (loop [current start acc 0]
    (let [acc' (if (check current) (inc acc) acc)]
      (if (= current end)
       acc'
       (recur (inc current) acc')))))

(defn solve-1 [filename]
  (->> filename
       read-by-line
       first
       parse
       calc))

(comment
  (solve-1 "resources/inputs/2019/04.txt")
  )
