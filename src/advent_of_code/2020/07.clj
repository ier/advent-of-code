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


(defn- trace
  ([bags item]
   (trace bags item (conj '() item)))
  ([bags item acc]
   (prn "trace2:" item)
   (let [found (filter #(fltr % item) bags)
         ffound (ffirst found)]
     (if (seq found)
       (recur bags ffound (conj acc ffound))
       acc))))


(defn- get-containers
  [bags pattern]
  (let [top (->> bags
                 (filter #(= pattern (first %))))
        direct (->> bags
                    (filter #(fltr % pattern)))
        indirect (mapv #(trace bags (first %)) direct)]
    #_(+ (count top)
       (count direct)
       (reduce + (map count indirect)))
    (concat (map first top)
            (map first direct)
            indirect)))


(defn solve
  [input-file-name pattern]
  (or (-> (->> input-file-name
               utils/->vec-of-str
               utils/->indexed-vec
               (map parse-line))
          (get-containers pattern))
      0))


(solve "resources/inputs/2020/07.txt" "shiny gold")