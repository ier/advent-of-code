(ns advent-of-code.2020.07
  (:require
   [clojure.string :as str]
   [advent-of-code.utils :as utils]))


(def ^:dynamic *debug?* false)


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
  (let [[_ left right idx] (re-find #"(.+) bags contain (.+):(\d+)" s)
        empty-children? (= "no other bags." right)
        children (if empty-children?
                   nil
                   (parse-bag right))
        result [left (filter some? children)]]
    (if *debug?*
      result
      (conj result idx))))


(defn fltr
  [item pattern]
  (->> item
       second
       (filter #(= pattern (:title %)))
       seq))


(defn- find-bags
  [bags patterns]
  (map
   (fn [pattern]
     (let [found (->> bags
                      (filter #(fltr % pattern))
                      (map first))]
       (if (seq found)
         (find-bags bags found)
         pattern)))
   patterns))


(defn- get-containers
  [bags pattern]
  (let [top (->> bags
                 (filter #(= pattern (first %)))
                 (map first))
        direct (->> bags
                    (filter #(fltr % pattern))
                    (map first))
        indirect (find-bags bags direct)]
    (count (concat top
                   direct
                   indirect))))


(defn solve
  [input-file-name pattern]
  (or (-> (->> input-file-name
               utils/->vec-of-str
               utils/->indexed-vec
               (map parse-line))
          (get-containers pattern))
      0))


(solve "resources/inputs/2020/07.txt" "shiny gold")