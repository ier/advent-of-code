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
        children (if empty-children? nil (parse-bag (second parts)))]
    [(first parts) (filter some? children)]))


#_(parse-line "light red bags contain 2 clear indigo bags, 3 light lime bags.")


#_(defn get-containers
    [xs s]
    (let [cleared (filter #(seq (second %)) xs)
          direct (get-parents cleared s)]
      (prn (str "intro: direct=" direct))
      (loop [acc (count direct)
             indirect (mapv #(get-parents cleared %) direct)]
        (prn (str "loop: indirect=" indirect))

        (doseq [m indirect]
          (prn-str m))

        (if (empty? indirect)
          acc
          (recur (+ acc (count indirect))
                 (map #(get-parents cleared %) indirect))))))


(defn- has-value
  "Returns a predicate that tests whether a map contains a specific value"
  [key value]
  (fn [m]
    (= value (m key))))


(defn- get-parents
  [xs s]
  (mapv first (filter #(contains? (set (second %)) s) xs)))


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


(defn solve []
  (let [pattern "shiny gold"
        lines (utils/file->vec-of-str "resources/inputs/2020/07.txt")
        all-bags (map parse-line lines)
        top (filter #(= pattern %) (->> all-bags (map first)))
        other (get-containers all-bags pattern 0)]
    #_(+ (count top) (count (flatten other)))
    other))


(solve)