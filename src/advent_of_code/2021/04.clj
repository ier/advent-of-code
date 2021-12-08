(ns advent-of-code.2021.04
  (:require
   [advent-of-code.utils :refer [->vec-of-str positions vec-remove]]
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


(defn- get-board-idx
  [results]
  (let [indexed (map-indexed list results)]
    (ffirst (filter #(= (second %) true) indexed))))


(defn- process
  [nums cells]
  (loop [idxs nums items cells]
    (let [val (first idxs)
          cells' (replace-with items val)
          done? (boolean (some #{true} (seq-complete? cells')))
          board-idx (get-board-idx (seq-complete? cells'))
          board (get (->> cells' (partition 25) vec) board-idx)]
      (if done?
        {:value (Integer/parseInt val)
         :sum (calculate board)
         :cells cells'
         :board-win-number board-idx}
        (recur (rest idxs) cells')))))


(comment
  (let [numbers ["10" "16" "13" "6" "15" "25" "12" "22" "18" "20" "8" "19" "3" "26" "1"]
        cells ["22" "13" "x" "x" "x"
               "8" "x" "x" "x" "x"
               "x" "x" "x" "x" "x"
               "6" "x" "3" "18" "x"
               "1" "12" "20" "15" "19"
               
               "3" "15" "x" "x" "22"
               "x" "18" "13" "x" "x"
               "19" "8" "x" "25" "x"
               "20" "x" "x" "x" "x"
               "x" "x" "x" "12" "6"]]
    (process numbers cells))
  )


(defn solve-1
  [filename]
  (let [{nums :numbers cells :boards} (->> filename ->vec-of-str parse-data)
        {value :value sum :sum} (process nums cells)]
    (* value sum)))


(defn- skip-numbers
  [value nums]
  (let [pos (first (positions #{value} nums))]
    (vec (drop (inc pos) nums))))


(defn solve-2
  [filename]
  (let [{nums :numbers
         cells :boards}
        (->> filename
             ->vec-of-str
             parse-data)
        
        {value :value
         cells :cells
         board-win-number :board-win-number}
        (process nums cells)
        
        cells' (->> cells
                    (partition 25)
                    vec
                    (vec-remove board-win-number)
                    (apply concat)
                    vec)
        numbers' (skip-numbers (str value) nums)
        res (process numbers' cells')]
    {:cells cells
    :numbers nums
    :res res}))


(comment
  ;; 1924 = 148 * 13
  (solve-2 "resources/inputs/2021/04-sample.txt")
  (solve-1 "resources/inputs/2021/04-sample.txt"))
