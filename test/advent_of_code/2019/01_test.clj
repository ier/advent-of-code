(ns advent-of-code.2019.01-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [advent-of-code.2019.01 :refer [solve-1 solve-2]]))

(deftest solve-2019-01-test
  (testing "solve-2019-01-test"
    (is (= 3478233
           (solve-1 "resources/inputs/2019/01.txt")))))

(deftest solve-2019-02-test
  (testing "solve-2019-02-test"
    (is (= 5214475
           (solve-2 "resources/inputs/2019/01.txt")))))
