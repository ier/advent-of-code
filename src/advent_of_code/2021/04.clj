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


(defn- check-part
  [v]
  (let [limit 5
        x-idxs [0 1 2 3 4]     ;; TODO: use range
        y-idxs [0 5 10 15 20]  ;; TODO: use mapped range
        xs (frequencies (map #(nth v (+ % limit)) x-idxs))
        ys (frequencies (map #(nth v (+ % 1)) y-idxs))]
    (or (= {"x" limit} xs)
        (= {"x" limit} ys))))


(defn- seq-complete?
  [xs]
  (let [part-size 25
        parts (partition part-size xs)]
    (check-part (first parts))))


(defn- process
  [nums cells]
  (loop [nums nums cells cells]
    (let [cells' (replace-with cells (first nums))]
      (if (seq-complete? cells')
        (let [filtered (filter #(not= "x" %) cells')]
          (reduce + (map #(Integer/parseInt %) filtered)))
        (recur (rest nums) cells')))))


(let [numbers ["7" "4" "9" "5" "11" "17" "23" "2" "0" "14" "21" "24" "10"
               "16" "13" "6" "15" "25" "12" "22" "18" "20" "8" "19" "3" "26" "1"]
      boards ["22" "13" "17" "11" "0" "8" "2" "23" "4" "24" "21" "9" "14" "16" "7" "6" "10" "3" "18" "5" "1" "12" "20" "15" "19"
              "3" "15" "0" "2" "22" "9" "18" "13" "17" "5" "19" "8" "7" "25" "23" "20" "11" "10" "24" "4" "14" "21" "16" "12" "6"
              "14" "21" "17" "24" "4" "10" "16" "15" "9" "19" "18" "8" "23" "26" "20" "22" "11" "13" "6" "5" "2" "0" "12" "3" "7"]]
  (process numbers boards))

(defn solve-1
  [filename]
  (let [{nums :numbers cells :boards} (->> filename ->vec-of-str parse-data)
        procesed (process nums cells)]
    procesed))


(comment
  #_(solve-2 "resources/inputs/2021/04.txt")
  (solve-1 "resources/inputs/2021/04-sample.txt")
  ;; 188 * 24 = 4512
  )