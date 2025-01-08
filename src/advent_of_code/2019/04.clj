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
  (loop [ints xs pre2 false pre false res []]
    (let [[f s] ints
          current (= f s)
          res' (conj res (and (false? pre2) (true? pre) (false? current)))]
      (if (some? s)
        (recur (next ints) pre current res')
        (some? (some (set res') [true]))))))

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

(defn process [filename]
  (->> filename
       read-by-line
       first
       parse))

(defn solve-1 [filename]
  (->> (process filename)
       (calc has-pair?)))

(defn solve-2 [filename]
  (->> (process filename)
       (calc has-pair-last?)))
