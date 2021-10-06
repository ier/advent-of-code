(ns advent-of-code.2019.02
  (:require
   [clojure.string :refer [split trim]]))


(defn replace-at
  [xs pos value]
  (let [parts (split-at pos xs)
        head (butlast (first parts))]
    (->> (conj (last parts)
               (str value)
               head)
         (filter some?)
         flatten)))


(defn- upd!
  [codes code]
  (let [fnx (fn [xs idx]
              (read-string (nth xs idx)))
        [op in1 in2 pos] (->> 4
                              range
                              (map #(fnx code %)))
        value (case op
                1 (+ (fnx codes in1) (fnx codes in2))
                2 (* (fnx codes in1) (fnx codes in2)))]
    (replace-at codes (inc pos) value)))


(defn- run [xs]
  (loop [codes xs idx 0]
    (let [code (->> codes
                    (drop idx)
                    (take 4))]
      (if (= "99" (first code))
        (->> codes
             first
             read-string)
        (recur (upd! codes code) (+ idx 4))))))


(defn init
  [data noun verb]
  (-> data
      (replace-at 2 noun)
      (replace-at 3 verb)))


(defn solve-1 [file-name]
  (run (-> file-name
           slurp
           trim
           (split #",")
           (init "12" "2"))))


(solve-1 "resources/inputs/2019/02.txt")