(ns advent-of-code.2020.06
  (:require
   [clojure.string :as str]
   [clojure.set :as set]
   [advent-of-code.utils :as utils]))

(defn- get-common-count [v]
  (cond
    (= 1 (count v))
    (count (first v))
    :else
    (let [etalon (set (first v))
          items (map set (rest v))
          intersected (apply set/intersection etalon items)]
      (count intersected))))


(defn solve []
  (let [init-lines (utils/file->vec-of-str "resources/inputs/2020/06.txt")
        lines (map #(if (= "" %) % (str " " %)) init-lines)
        updated (map utils/replace-if-empty lines)
        content (reduce str updated)
        splitted* (str/split content #"_")

        cleared (map #(str/replace % #" " "") splitted*)
        grouped* (map #(reduce str (distinct %)) cleared)
        grouped** (map str grouped*)
        counted (map count grouped**)
        result1 (reduce + counted)

        fixed (map str/trim splitted*)
        splitted** (map #(str/split % #" ") fixed)
        anayzed (map #(get-common-count %) splitted**)

        result2 (reduce + anayzed)]
    [result1 result2]))


(solve)