(ns advent-of-code.2021.04-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [advent-of-code.2021.04 :refer [solve-1 solve-2]]))


(deftest solve-1-test
  (testing "solve 04 part 1"
    (is (= 4512
           (solve-1 "resources/inputs/2021/04-sample.txt")))
    (is (= 89001
           (solve-1 "resources/inputs/2021/04.txt")))))


(deftest solve-2-test
  (testing "solve 03 part 2"
    (is (= 1924
           (solve-2 "resources/inputs/2021/04-sample.txt")))
    #_(is (= ?
           (solve-2 "resources/inputs/2021/04.txt")))))
