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


(defn fltr
  [item pattern]
  (->> item
       second
       (filter #(= pattern (:title %)))
       seq))


(defn- find-bag
  [bags pattern]
  (->> bags
       (filter #(fltr % pattern))
       (map first)))


(defn- find-bags
  [bags pattern]
  (let [found (find-bag bags pattern)]
    (if (seq found)
      (map #(find-bags bags %) found)
      pattern)))


(defn solve
  [input-file-name pattern]
  (let [bags (->> input-file-name
                  ->vec-of-str
                  (map parse-line))
        total (->> (find-bag bags pattern)
                   (map #(find-bags bags %))
                   flatten)]
    (count total)))


(solve "resources/inputs/2020/07-test-sample.txt" "shiny gold")
