(ns advent-of-code.2021.03-text
  (:require
   [clojure.test :refer [deftest testing is]]
   [advent-of-code.2021.03 :refer [solve-1 #_solve-2]]))


(deftest solve-1-test
  (testing "solve 03 part 1"
    (is (= 2967914
           (solve-1 "resources/inputs/2021/03.txt")))))

#_(deftest solve-2-test
  (testing "solve 03 part 2"
    (is (= ?
           (solve-2 "resources/inputs/2021/03-sample.txt")))
    (is (= ?
           (solve-2 "resources/inputs/2021/03.txt")))))
