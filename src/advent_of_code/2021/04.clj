(ns advent-of-code.2021.04
  (:require
   [advent-of-code.utils :refer [->vec-of-str positions]]
   [clojure.string :as str]))


(defn- parse-data
  [data]
  (let [numbers (str/split (first data) #",")
        boards (->> data
                    rest
                    (filter (complement str/blank?))
                    (map #(str/split (str/trim %) #"\s+"))
                    (apply concat)
                    vec)]
    {:numbers numbers
     :boards boards}))


(defn- replace-with
  ;; (replace-with ["22" "13" "17" "11" "13"] "13")
  [xs val]
  (let [pos (positions #{val} xs)]
    (loop [idxs pos items xs]
      (if (nil? (first idxs))
        items
        (recur (rest idxs) (assoc items (first idxs) "x"))))))


(defn found?
  [xs ids]
  (boolean
   (seq (filter
         #(= {"x" 5} %)
         (map frequencies
              (map
               (fn [x]
                 (map #(nth xs %) x))
               ids))))))


(defn- scan-x
  [xs x-idxs]
  (let [ids (map
             (fn [n]
               (map
                #(+ (* n 5) %)
                x-idxs))
             [0 1 2 3 4])]
    (found? xs ids)))


(defn- scan-y
  [xs y-idxs]
  (let [ids (map
             (fn [n]
               (map
                #(+ n %)
                y-idxs))
             [0 1 2 3 4])]
    (found? xs ids)))


(defn- check-part
  [v]
  (or (scan-x v [0 1 2 3 4])
      (scan-y v [0 5 10 15 20])))


(defn- seq-complete?
  [xs]
  (->> xs
       (partition 25)
       (map check-part)))


(defn- calculate
  [cells]
  (let [filtered (filter #(not= "x" %) cells)]
    (reduce + (map #(Integer/parseInt %) filtered))))


(defn- process
  [nums cells]
  (loop [idxs nums items cells]
    (let [val (first idxs)
          cells' (replace-with items val)
          done? (boolean (some #{true} (seq-complete? cells')))]
      (if done?
        {:number val
         :sum (calculate cells')}
        (recur (rest idxs) cells')))))


(defn solve-1
  [filename]
  (let [{nums :numbers cells :boards} (->> filename ->vec-of-str parse-data)
        procesed (process nums cells)]
    procesed))


(comment
  #_(solve-2 "resources/inputs/2021/04.txt")
  (solve-1 "resources/inputs/2021/04-sample.txt")
  ;; -188 * +24 = 4512
  )