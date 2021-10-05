(ns advent-of-code.2020.04
  (:require
   [clojure.string :refer [split blank?]]
   [advent-of-code.utils :refer [->vec-of-str
                                 replace-if-empty]]))


(defn- sanitize [s]
  (let [s' (split s #":")
        k (keyword (first s'))
        v (str (second s'))]
    (list k v)))


(defn- ->map [s]
  (let [pairs (split s #" ")
        pairs* (map sanitize pairs)
        fnx (fn [m p]
              (assoc m (first p) (second p)))]
    (reduce fnx {} pairs*)))


(defn- has-keys? [m]
  (or
   (and
    (contains? m :byr)
    (contains? m :iyr)
    (contains? m :eyr)
    (contains? m :hgt)
    (contains? m :hcl)
    (contains? m :ecl)
    (contains? m :pid)
    (contains? m :cid))
   (and
    (contains? m :byr)
    (contains? m :iyr)
    (contains? m :eyr)
    (contains? m :hgt)
    (contains? m :hcl)
    (contains? m :ecl)
    (contains? m :pid))))


(defn- valid-hgt? [s]
  (if (blank? s)
    false
    (if (re-matches #"^\d+$" s)
      false
      (let [len (count s)
            shift (- len 2)
            value (read-string (subs s 0 shift))
            units (subs s shift (+ shift 2))]
        (or
         (and (= units "cm") (>= value 150) (<= value 193))
         (and (= units "in") (>= value 59) (<= value 76)))))))


(defn- valid-hcl? [s]
  (if (blank? s)
    false
    (some? (re-matches #"^#[0-9a-fA-F]{6}$" s))))


(defn- valid-ecl? [s]
  (if (blank? s)
    false
    (and
     (contains? #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} s)
     (= (count s) 3))))


(defn- valid-pid? [s]
  (if (blank? s)
    false
    (some? (re-matches #"^\d{9}$" s))))


(defn- valid-id? [m]
  (let [byr (if (some? (:byr m)) (read-string (:byr m)) 0)
        iyr (if (some? (:iyr m)) (read-string (:iyr m)) 0)
        eyr (if (some? (:eyr m)) (read-string (:eyr m)) 0)
        hgt (:hgt m)
        hcl (:hcl m)
        ecl (:ecl m)
        pid (:pid m)]
    (and
     (and (>= byr 1920) (<= byr 2002))
     (and (>= iyr 2010) (<= iyr 2020))
     (and (>= eyr 2020) (<= eyr 2030))
     (valid-hgt? hgt)
     (valid-hcl? hcl)
     (valid-ecl? ecl)
     (valid-pid? pid))))


(defn parse-credentials [file-name]
  (let [init-lines (->vec-of-str file-name)
        lines (map #(if (= "" %) % (str " " %)) init-lines)
        updated (map replace-if-empty lines)
        content (reduce str updated)
        splitted* (split content #"_")
        splitted (map #(subs % 1 (count %)) splitted*)
        hashmaps (map ->map splitted)]
    hashmaps))


(defn solve [file-name]
  (let [credentials (parse-credentials file-name)
        filtered1 (filter has-keys? credentials)
        result1 (count filtered1)
        filtered2 (filter valid-id? credentials)
        result2 (count filtered2)]
    [result1 result2]))


(comment
  (solve "resources/inputs/2020/04.txt")
  )
