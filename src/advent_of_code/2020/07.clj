(ns advent-of-code.2020.07
  (:require
   [clojure.string :as str]
   [advent-of-code.utils :refer [->vec-of-str]]))


(defn- ->hashmap [s]
  (let [[_ amount title] (re-find #"(\d+) (.+$)" s)]
    (when (some? amount)
      {:title title :amount (Integer/parseInt amount)})))


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


(defn- get-bag
  [rule pattern]
  (when (some
         (fn [{:keys [title]}]
           (= pattern title))
         (second rule))
    (first rule)))


(defn- filter-rules
  [rules pattern]
  (->> rules
       (map #(get-bag % pattern))
       (keep not-empty)))


(defn- search
  [rules patterns acc]
  (reduce
   conj
   (list)
   (map
    (fn [pattern]
      (let [items (filter-rules rules pattern)]
        (if (seq items)
          (search rules items (reduce conj acc items))
          acc)))
    patterns)))


(defn- parse-rules
  [file-name]
  (->> file-name
       ->vec-of-str
       (map parse-line)))


(defn solve-1
  [input-file-name pattern]
  (-> input-file-name
      parse-rules
      (search (list pattern) (list))
      flatten
      distinct
      count))


(defn- fltr
  [rules pattern]
  (filter #(->> % first (= pattern)) rules))


(defn- sum
  [found]
  (reduce + (map #(->> % :amount) (second found))))


(defn- walk
  [rules patterns acc]
  (reduce
   conj
   acc
   (map
    (fn [pattern]
      (let [found (->> pattern (fltr rules) first)]
        (if (seq found)
          (walk (remove #(->> % first (= pattern)) rules)
                (->> found last (map #(:title %)))
                (conj acc (sum found)))
          acc)))
    patterns)))



(defn solve-2
  [input-file-name pattern]
  (-> input-file-name
      parse-rules
      (walk (list pattern) (list))
      ))


(comment
  (solve-1 "resources/inputs/2020/07-1-test-sample.txt" "shiny gold")
  (solve-2 "resources/inputs/2020/07-2-test-sample.txt" "shiny gold")
  (solve-2 "resources/inputs/2020/07.txt" "shiny gold")
  )
