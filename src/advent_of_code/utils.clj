(ns advent-of-code.utils
  (:require
   [clojure.string :as str]))


(defn ->vec-of-str [filename]
  (->> filename
       slurp
       str/split-lines))

(defn ->indexed-vec [v]
  (map-indexed (fn [idx itm] (str itm ":" (inc idx))) v))


(defn replace-if-empty [s]
  (if (= s "") "_" s))