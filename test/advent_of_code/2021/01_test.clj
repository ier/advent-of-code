(ns advent-of-code.2021.01-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [advent-of-code.2021.01 :refer [solve-1 solve-2]]))


(deftest solve-1-test
  (testing "solve 01 part 1"
    (is (= 1516
           (solve-2 "resources/inputs/2021/01.txt")))))


(deftest solve-1-test
  (testing "solve 01 part 1"
    (is (= 1475
           (solve-1 "resources/inputs/2021/01.txt")))))