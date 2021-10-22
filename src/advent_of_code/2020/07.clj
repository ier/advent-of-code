(ns advent-of-code.2020.07
  (:require
   [clojure.string :as str]
   [advent-of-code.utils :refer [->vec-of-str]]))


(defn- ->hashmap [s]
  (let [[_ amount title] (re-find #"(\d+) (.+$)" s)]
    (when (some? amount)
      {:amount amount :title title})))


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


(defn solve
  [input-file-name pattern]
  (let [bags (->> input-file-name
                  ->vec-of-str
                  (map parse-line))
        direct _
        indirect _]
    (concat direct indirect)))


;; https://github.com/callum-oakley/advent-of-code-2020/blob/master/src/day_07.clj
(solve "resources/inputs/2020/07-1-test-sample.txt" "shiny gold")
