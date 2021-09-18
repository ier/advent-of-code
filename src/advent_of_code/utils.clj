(ns advent-of-code.utils
  (:require
   [clojure.string :as str]))


(defn file->vec-of-str [filename]
  (->> filename
       slurp
       str/split-lines))