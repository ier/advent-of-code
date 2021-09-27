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


(defn fltr
  [item pattern]
  (->> item
       second
       (filter #(= pattern (:title %)))
       seq))


(defn- trace
  ([bags item]
   (trace bags item (conj '() item)))
  ([bags item acc]
   (let [found (filter #(fltr % item) bags)]
     (if (seq found)
       (recur bags (ffirst found) (conj acc (ffirst found)))
       acc))))


(defn- get-containers
  [bags pattern]
  (let [top (filter #(= pattern (first %)) bags)
        direct (filter #(fltr % pattern) bags)
        indirect (map #(trace bags (first %)) direct)]
    (+ (count top)
       (count direct)
       (reduce + (map count indirect)))))


(defn solve
  [input-file-name pattern]
  (or (-> (->> input-file-name
               utils/->vec-of-str
               (map parse-line))
          (get-containers pattern))
      0))


(comment
  (solve "resources/inputs/2020/07.txt" "shiny gold")
  )
