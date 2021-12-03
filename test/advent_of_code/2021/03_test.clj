(ns advent-of-code.2021.03-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [advent-of-code.2021.03 :refer [solve-1 solve-2]]))


(deftest solve-1-test
  (testing "solve 03 part 1"
    (is (= 2967914
           (solve-1 "resources/inputs/2021/03.txt")))))


(deftest solve-2-test
  (testing "solve 03 part 2"
    (is (= 230
           (solve-2 "resources/inputs/2021/03-sample.txt")))
    (is (= 7041258
           (solve-2 "resources/inputs/2021/03.txt")))))
