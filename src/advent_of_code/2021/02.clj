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


(defn solve-1 [filename]
  (let [data (->vec-of-str filename)]
    (loop [vals data x 0 y 0]
      (if (nil? (first vals))
        (* x y)
        (let [[x' y'] (delta (first vals))]
          (recur (rest vals) (+ x x') (+ y y')))))))


(defn- delta-aim [s aim]
  (let [[direction step] (str/split s #" ")
        step* (Integer/parseInt step)]
    (cond
      (= "up" direction) [0 0 (- aim step*)]
      (= "down" direction) [0 0 (+ aim step*)]
      (= "forward" direction) [step* (* step* aim) aim])))


(defn solve-2 [filename]
  (let [data (->vec-of-str filename)]
    (loop [vals data x 0 y 0 aim 0]
      (if (nil? (first vals))
        (* x y)
        (let [[x' y' aim'] (delta-aim (first vals) aim)]
          (recur (rest vals) (+ x x') (+ y y') aim'))))))


(comment
  (solve-2 "resources/inputs/2021/02.txt")
  (solve-1 "resources/inputs/2021/02.txt"))
