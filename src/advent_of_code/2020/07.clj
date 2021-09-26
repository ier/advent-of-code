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


(defn- has-bag?
  [bags item pattern]
  (let [fltr (fn [item pattern]
               (let [xs (filter
                         (fn [m] (= pattern (:title m)))
                         (second item))]
                 (when (seq xs)
                   (first item))))
        res (or (= pattern (first item))
                (->> bags
                     (map #(fltr % pattern))
                     (filter seq)
                     seq)
                #_(...))]
    res))


(defn- get-containers
  [bags pattern]
  (loop [items bags
         cnt 0
         acc 0]
    (if (seq items)
      (if (has-bag? bags (first items) pattern)
        (recur (rest items) (inc cnt) (inc acc))
        (recur (rest items) (inc cnt) acc))
      acc)))


#_(let [fltr (fn [item pattern]
               (let [vv (filter #(= pattern (:title %)) (second item))]
                 (when (seq vv) (first item))))
        direct (->> bags
                    (map #(fltr % pattern))
                    (filter seq))]
    (if (seq direct)
      (mapv #(get-containers bags % (+ acc (count direct))) direct)
      acc))


(defn solve
  [input-file-name pattern]
  (or (-> (->> input-file-name
               utils/->vec-of-str
               (map parse-line))
          (get-containers pattern)
          #_flatten
          #_distinct
          #_count)
      0))


(comment
  (solve "resources/inputs/2020/07-test-sample.txt" "shiny gold")
  )
