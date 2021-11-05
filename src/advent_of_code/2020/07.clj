(ns advent-of-code.2020.07
  (:require
   [clojure.string :as str]
   [advent-of-code.utils :refer [->vec-of-str]]))


(defn- ->hashmap [s]
  (let [[_ amount title] (re-find #"(\d+) (.+$)" s)]
    (when (some? amount)
      (hash-map title amount))))


(defn- fix [xs]
  (map #(-> %
            (str/replace " bags." "")
            (str/replace " bags" "")
            (str/replace " bag." "")
            (str/replace " bag" "")) xs))


(defn- parse-bag [s]
  (map ->hashmap (-> s (str/split #", ") fix)))


(defn- parse-line [s]
  (let [[_ left right] (re-find #"(.+) bags contain (.+)" s)
        children (if (= "no other bags." right)
                   nil
                   (parse-bag right))]
    [left (filter some? children)]))


(defn- indirect
  ([rules pattern]
   (indirect rules pattern 0))
  ([rules pattern acc]
   (let [right-matches nil #_(filter #(= pattern ) rules)]
     right-matches)))


(defn solve
  [input-file-name pattern]
  (let [rules (->> input-file-name
                   ->vec-of-str
                   (map parse-line))
        direct (filter #(= pattern (first %)) rules)
        indirect (indirect rules pattern)]
    (count (concat direct indirect))))


;; https://github.com/callum-oakley/advent-of-code-2020/blob/master/src/day_07.clj
(solve "resources/inputs/2020/07-1-test-sample.txt" "shiny gold")
