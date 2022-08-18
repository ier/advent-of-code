(ns advent-of-code.2019.01
  (:require
   [advent-of-code.utils :refer [->vec-of-str]]))


(defn- calc [n]
  (-> n
      (quot 3)
      (- 2)))


(defn solve-1 [file-name]
  (reduce +
          (map
           #(-> %
                read-string
                calc)
           (->vec-of-str file-name))))


(defn solve-2 [file-name]
  (reduce +
          (map
           #(loop [n (read-string %) acc 0]
              (let [c (calc n)]
                (if (pos? c)
                  (recur c (+ acc c))
                  acc)))
           (->vec-of-str file-name))))


(comment
  (solve-1 "resources/inputs/2019/01.txt")

  (solve-2 "resources/inputs/2019/01.txt")
  )
