(ns advent-of-code.2020.06
  (:require
   [clojure.string :refer [split replace trim]]
   [clojure.set :refer [intersection]]
   [advent-of-code.utils :refer [->vec-of-str
                                 replace-if-empty]]))

(defn- get-common-count [v]
  (cond
    (= 1 (count v))
    (count (first v))
    :else
    (let [etalon (set (first v))
          items (map set (rest v))
          intersected (apply intersection etalon items)]
      (count intersected))))


(defn solve [file-name]
  (let [init-lines (->vec-of-str file-name)
        lines (map #(if (= "" %) % (str " " %)) init-lines)
        updated (map replace-if-empty lines)
        content (reduce str updated)
        splitted* (split content #"_")

        cleared (map #(replace % #" " "") splitted*)
        grouped* (map #(reduce str (distinct %)) cleared)
        grouped** (map str grouped*)
        counted (map count grouped**)
        result1 (reduce + counted)

        fixed (map trim splitted*)
        splitted** (map #(split % #" ") fixed)
        anayzed (map #(get-common-count %) splitted**)

        result2 (reduce + anayzed)]
    [result1 result2]))


(comment
  (solve "resources/inputs/2020/06.txt")
  )
