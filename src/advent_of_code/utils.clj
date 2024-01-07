(ns advent-of-code.utils
  (:require
   [clojure.string :as str]
   [clojure.java.io :as io]))

(defn ->vec-of-str
  [filename]
  (->> filename
       slurp
       str/split-lines))

(defn read-by-line
  [filename]
  (with-open [rdr (io/reader filename)]
    (reduce conj [] (line-seq rdr))))

(defn ->indexed-vec
  [v]
  (map-indexed (fn [idx itm] (str itm ":" (inc idx))) v))

(defn replace-if-empty
  [s]
  (if (= s "") "_" s))

(defn positions
  [pred coll]
  (keep-indexed
   (fn [idx x]
     (when (pred x)
       idx))
   coll))

 (defn vec-remove
   "remove elem in coll"
   [pos coll]
   (into (subvec coll 0 pos) (subvec coll (inc pos))))
