(ns advent-of-code.2019.02
  (:require
   [clojure.string :refer [split trim]]))

(defn- replace-at
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
        [op in1 in2 pos] (->> (range 4)
                              (map #(fnx code %)))
        value (case op
                1 (+ (fnx codes in1) (fnx codes in2))
                2 (* (fnx codes in1) (fnx codes in2)))]
    (replace-at codes (inc pos) value)))

(defn run
  [xs]
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

(defn parse
  [file-name]
  (-> file-name
      slurp
      trim
      (split #",")))

(defn solve-1
  [file-name]
  (-> file-name
      parse
      (init "12" "2")
      run))

(comment
  (solve-1 "resources/inputs/2019/02.txt")
  )

(defn- find-match
  [data target]
  (let [size (count data)]
    (->> (for [noun (range size)
               verb (range size)]
           (let [found (-> data
                           (init noun verb)
                           run
                           (= target))]
             (when found
               (prn "Match found for " noun " and " verb "."))))
         (slurp "result.txt"))))

(defn solve-2
  [file-name]
  (let [target 19690720]
    (-> file-name
        parse
        (find-match target))))


(comment
  (solve-2 "resources/inputs/2019/02.txt")
  )
