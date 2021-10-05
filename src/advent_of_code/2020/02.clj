(ns advent-of-code.2020.02
  (:require
   [clojure.string :refer [split]]
   [advent-of-code.utils :refer [->vec-of-str]]))


(defn- count-substring [txt sub]
  (count (re-seq (re-pattern sub) txt)))


(defn- parse-string [s]
  (let [parts (split s #"\ ")
        interval (split (first parts) #"-")
        min (read-string (first interval))
        max (read-string (second interval))
        char (subs (second parts) 0 1)
        pwd (last parts)
        matches (count-substring pwd char)
        valid-v1? (<= min matches max)
        first-match? (= (subs pwd (dec min) min) char)
        second-match? (= (subs pwd (dec max) max) char)
        valid-v2? (not= first-match? second-match?)]
    (assoc {}
           :min min
           :max max
           :char char
           :pwd pwd
           :matches matches
           :valid-v1? valid-v1?
           :first-match? first-match?
           :second-match? second-match?
           :valid-v2? valid-v2?)))


(defn solve [file-name]
  (let [data (->vec-of-str file-name)
        xs (into [] (map parse-string data))
        result1 (count (filter #(:valid-v1? %) xs))
        result2 (count (filter #(:valid-v2? %) xs))]
    [result1 result2]))


(comment
  (solve "resources/inputs/2020/02.txt")
  )
