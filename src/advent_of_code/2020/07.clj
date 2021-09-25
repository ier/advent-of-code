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
  (let [parts (str/split s #" bags contain ")
        empty-children? (= "no other bags." (:second parts))
        children (if empty-children?
                   nil
                   (parse-bag (second parts)))]
    [(first parts) (filter some? children)]))


(defn- fltr
  [item pattern]
  (let [vv (filter (fn [m]
                     (= pattern (:title m))) (second item))]
    (when (seq vv) (first item))))


(defn- get-containers
  [bags pattern acc]
  (let [direct (->> bags
                    (map #(fltr % pattern))
                    (filter seq))]
    (if (seq direct)
      (mapv
       #(get-containers bags % (+ acc (count direct)))
       direct)
      acc)))


(defn solve
  [input-file-name pattern]
  (let [lines (utils/file->vec-of-str input-file-name)
        all-bags (map parse-line lines)
        other (get-containers all-bags pattern 0)]
    (first (distinct (flatten other)))))


(solve "resources/inputs/2020/07.txt" "shiny gold")