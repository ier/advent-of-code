(ns advent-of-code.2021.02
  (:require
   [advent-of-code.utils :refer [->vec-of-str]]
   [clojure.string :as str]))


(defn- delta [s]
  (let [[direction step] (str/split s #" ")
        step* (Integer/parseInt step)]
    (cond
      (= "up" direction) [0 (- step*)]
      (= "down" direction) [0 step*]
      (= "forward" direction) [step* 0])))


(defn solve [filename]
  (let [data (->vec-of-str filename)]
    (loop [vals data x 0 y 0]
      (if (nil? (first vals))
        (* x y)
        (let [[x' y'] (delta (first vals))]
          (recur (rest vals) (+ x x') (+ y y')))))))


(comment
  (solve "resources/inputs/2021/02.txt"))
