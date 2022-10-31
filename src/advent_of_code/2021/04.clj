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


(defn- process-first
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


(defn solve-1
  [filename]
  (let [{nums :numbers cells :boards} (->> filename ->vec-of-str parse-data)
        {value :value sum :sum} (process-first nums cells)]
    (* value sum)))


(defn- process-last
  [idxs items]
  (loop [nums idxs cells items]
    (let [{value :value cls :cells board-win-number :board-win-number sum :sum} (process-first nums cells)]
      (if (= (count cls) 25)
        (* value sum)
        (recur (rest nums) (->> cls
                                (partition 25)
                                vec
                                (vec-remove board-win-number)
                                (apply concat)
                                vec))))))


(defn solve-2
  [filename]
  (let [{nums :numbers cells :boards} (->> filename ->vec-of-str parse-data)]
    (process-last nums cells)))


(comment
  (solve-2 "resources/inputs/2021/04-sample.txt")
  (solve-1 "resources/inputs/2021/04-sample.txt")
  )
