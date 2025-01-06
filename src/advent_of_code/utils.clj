(ns advent-of-code.utils
  (:require
   [clojure.string :as str]
   [clojure.java.io :as io]))

(defn digits
  "From here: https://batsov.com/articles/2022/08/01/clojure-tricks-number-to-digits/
   1. Classic recursion variant:
      (defn digits [n]
        (if (< n 10)
          [n]
          (conj (digits (quot n 10)) (rem n 10))))
   2. Simple conversion variant:
      (defn digits [n]
        (map #(read-string (str %)) (str n)))
   3. Leveraging Java's API:
      (defn digits [n]
        (map #(Character/digit % 10) (str n)))
   4. Naive variant:
      (defn digits [x]
         (->> (split (str x) #\"\")
              (map #(Integer/parseInt %))))
   5. Clever trick variant:"
  [n]
  (map
   #(- (int %) 48)
   (str n)))

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
  (map-indexed
   (fn [idx itm]
     (str itm ":" (inc idx)))
   v))

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
