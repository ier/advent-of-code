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


(defn- min-freq-val
  [xs]
  (let [freqs (->> xs frequencies)
        groups (group-by second freqs)]
    (if (= (count groups) 1)
      (apply min xs)
      (->> freqs
           (apply min-key val)
           key))))


(defn- most-common
  [data coll-number]
  (let [digits (mapv #(nth % coll-number) data)]
    (max-freq-val digits)))


(defn- least-common
  [data coll-number]
  (let [chars (mapv #(nth % coll-number) data)
        digits (map #(-> % str Integer/parseInt) chars)]
    (min-freq-val digits)))


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


(defn- fltr-most
  [data]
  (loop [rows data acc ""]
    (let [x (most-common rows (count acc))
          acc' (str acc x)
          rows' (filter #(str/starts-with? % acc') rows)]
      (if (= 1 (count rows'))
        acc'
        (recur rows' acc')))))

(defn- fltr-least
  [data]
  (loop [rows data acc ""]
    (let [x (least-common rows (count acc))
          acc' (str acc x)
          rows' (filter #(str/starts-with? % acc') rows)]
      (if (= 1 (count rows'))
        (first rows')
        (recur rows' acc')))))


(defn solve-2
  [filename]
  (let [data (->vec-of-str filename)
        most-bin (fltr-most data)
        most-int (Integer/parseInt most-bin 2)
        least-bin (fltr-least data)
        least-int (Integer/parseInt least-bin 2)]
    (* most-int least-int)))


(comment
  (solve-2 "resources/inputs/2021/03.txt")
  (solve-1 "resources/inputs/2021/03.txt"))