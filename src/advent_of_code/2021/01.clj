(ns advent-of-code.2021.01
  (:require
   [advent-of-code.utils :refer [->vec-of-str]]))


(defn- sum-up
  [data width]
  (loop [vals data acc []]
    (let [pair (take width vals)]
      (if (= (count pair) width)
        (let [frst (Integer/parseInt (first pair))
              scnd (Integer/parseInt (second pair))
              third (Integer/parseInt (last pair))
              sum (+ frst scnd third)]
          (recur (rest vals) (conj acc sum)))
        acc))))


(defn solve-2 [filename]
  (let [data (->vec-of-str filename)
        sum (sum-up data 3)]
    (loop [vals sum acc 0]
      (let [pair (take 2 vals)]
        (if (= (count pair) 2)
          (let [frst (first pair)
                scnd (second pair)
                increese? (< frst scnd)]
            (recur (rest vals) (if increese? (inc acc) acc)))
          acc)))))


(defn solve-1 [filename]
  (let [data (->vec-of-str filename)]
    (loop [vals data acc 0]
      (let [pair (take 2 vals)]
        (if (= (count pair) 2)
          (let [frst (Integer/parseInt (first pair))
                scnd (Integer/parseInt (second pair))
                increese? (< frst scnd)]
            (recur (rest vals) (if increese? (inc acc) acc)))
          acc)))))


(comment
  (solve-2 "resources/inputs/2021/01.txt")
  (solve-1 "resources/inputs/2021/01.txt"))