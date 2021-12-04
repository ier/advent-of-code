(ns advent-of-code.2021.04
  (:require
   [advent-of-code.utils :refer [->vec-of-str positions]]
   [clojure.string :as str]))


(defn- parse-data
  [data]
  (let [numbers (str/split (first data) #",")
        boards (map #(str/split (str/trim %) #"\s+") (filter (complement str/blank?) (rest data)))]
    {:numbers numbers
     :boards boards}))


(defn- replace-with
  ;; (replace-with ["22" "13" "17" "11" "13"] "13")
  [xs val]
  (let [pos (positions #{val} xs)]
    (loop [idxs pos items xs]
      (prn (first idxs))
      (if (nil? (first idxs))
        items
        (recur (rest idxs) (assoc items (first idxs) "x"))))))




;;(reduce # pos)
;; 0, 1, 2, 3, 4 | x0 x1 x2 x3 x4
;; 

;; 0, 5, 10, 15, 20 | x0 x5 x10 x15 x20


(defn solve-1
  [filename]
  (let [{numbers :numbers boards :boards} (->> filename ->vec-of-str parse-data)
        res (map #(replace-with boards %) numbers)]
    res))






(comment
  #_(solve-2 "resources/inputs/2021/04.txt")
  (solve-1 "resources/inputs/2021/04-sample.txt")
  ;; 188 * 24 = 4512
  )