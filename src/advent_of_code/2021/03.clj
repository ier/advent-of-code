(ns advent-of-code.2021.03
  (:require
   [advent-of-code.utils :refer [->vec-of-str]]
   [clojure.string :as str]))


(defn- max-freq-val
  ;; (max-freq-val [0 1 1 0 1 1 0])
  [xs]
  (->> xs
       frequencies
       (apply max-key val)
       key))

(defn- flip-bits
  ;; "001110101101"
  [s]
  (let [vals (str/split s #"")
        ints (map #(bit-flip (Integer/parseInt %) 0) vals)]
    (reduce str ints)))

(defn- most-common
  [data coll-number]
  (let [digits (mapv #(nth % coll-number) data)]
    (max-freq-val digits)))


(defn solve-1
  [filename]
  (let [data (->vec-of-str filename)
        colls-count (->> data first count)
        idxs (range 0 colls-count)
        res (map #(most-common data %) idxs)
        gamma-bin (apply str res)
        gamma-int (Integer/parseInt gamma-bin 2)
        epsilon-bin (flip-bits gamma-bin)
        epsilon-int (Integer/parseInt epsilon-bin 2)]
    (* gamma-int epsilon-int)))


(comment
  (solve-1 "resources/inputs/2021/03.txt"))