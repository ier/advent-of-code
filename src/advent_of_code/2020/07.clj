(ns advent-of-code.2020.07
  (:require
   [clojure.string :as str]
   [advent-of-code.utils :as utils]))


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


(defn fltr
  [item pattern]
  (->> item
       second
       (filter #(= pattern (:title %)))
       seq))


(defn- find-bags
  [bags pattern]
  (let [found (->> bags
                   (filter #(fltr % pattern))
                   (map first))]
    (if (seq found)
      (map #(find-bags bags %) found)
      pattern)))


(defn solve
  [input-file-name pattern]
  (let [bags (->> input-file-name
                  utils/->vec-of-str
                  (map parse-line))
        top (->> bags
                 (filter #(= pattern (first %)))
                 (map first))
        direct (find-bags bags pattern)
        indirect (map #(find-bags bags %) direct)]
    (concat top direct indirect)))


(solve "resources/inputs/2020/07-test-sample.txt" "shiny gold")
