(ns advent-of-code.2021.02-test
(:require
 [clojure.test :refer [deftest testing is]]
 [advent-of-code.2021.02 :refer [solve]]))


(deftest solve-1-test
  (testing "solve 02 part 1"
    (is (= 1692075
           (solve "resources/inputs/2021/02.txt")))))
