(ns advent-of-code.2021.01
  (:require
   [advent-of-code.utils :refer [->vec-of-str]]))

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
  (solve-1 "resources/inputs/2021/01.txt"))