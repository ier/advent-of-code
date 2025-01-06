(ns advent-of-code.2019.04-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [advent-of-code.2019.04 :refer [solve-1]]))

(deftest solve-2019-04-01-test
  (testing "solve-2019-04-01"
    (is (= 1929
           (solve-1 "resources/inputs/2019/04.txt")))))
