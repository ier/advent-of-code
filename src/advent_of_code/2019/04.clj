(ns advent-of-code.2019.04
  (:require
   [clojure.string :refer [split]]
   [advent-of-code.utils :refer [read-by-line digits]]))

(defn parse [s]
  (->> (split s #"-")
       (map #(Integer/parseInt %))))

(defn has-pair? [xs]
  (loop [ints xs]
    (if (> (count ints) 1)
      (let [[f s] ints]
        (if (= f s)
          true
          (recur (next ints))))
      false)))

(defn has-pair-last? [xs]
  (loop [ints xs]
    (if (> (count ints) 1)
      (let [[f s] ints]
        (if (= f s)
          true
          (recur (next ints))))
      false)))

(defn check [x f]
  (let [nums (digits x)]
    (and
     (apply <= nums)
     (f nums))))

(defn calc
  [f [start end]]
  (loop [current start acc 0]
    (let [acc' (if (check current f) (inc acc) acc)]
      (if (= current end)
       acc'
       (recur (inc current) acc')))))

(defn solve-1 [filename]
  (->> filename
       read-by-line
       first
       parse
       (calc has-pair?)))

(defn solve-2 [filename]
  (->> filename
       read-by-line
       first
       parse
       (calc has-pair-last?)))
