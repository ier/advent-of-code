(ns advent-of-code.2021.02-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [advent-of-code.2021.02 :refer [solve-1 solve-2]]))


(deftest solve-1-test
  (testing "solve 02 part 1"
    (is (= 1692075
           (solve-1 "resources/inputs/2021/02.txt")))))

(deftest solve-2-test
  (testing "solve 02 part 2"
    (is (= 900
           (solve-2 "resources/inputs/2021/02-sample.txt")))
    (is (= 1749524700
           (solve-2 "resources/inputs/2021/02.txt")))))
