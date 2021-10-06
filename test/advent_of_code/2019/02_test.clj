(ns advent-of-code.2019.02-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [advent-of-code.2019.02 :refer [solve-1]]))


(deftest solve-2019-01-test
  (testing "solve-2019-01-test"
    (is (= 3267740
           (solve-1 "resources/inputs/2019/02.txt")))))
