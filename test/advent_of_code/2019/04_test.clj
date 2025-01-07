(ns advent-of-code.2019.04-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [advent-of-code.2019.04 :refer [solve-1 has-pair-last? solve-2]]
   [advent-of-code.utils :refer [digits]]))

(deftest solve-2019-04-01-test
  (testing "solve-2019-04-01"
    (is (= 1929
           (solve-1 "resources/inputs/2019/04.txt")))))

(deftest solve-2019-04-02-test
  (testing "solve-2019-04-02"
    (is (= false (has-pair-last? (digits 111111))))
    (is (= false (has-pair-last? (digits 111222))))
    (is (= true (has-pair-last? (digits 112211))))
    (is (= true (has-pair-last? (digits 112222))))
    (is (= true (has-pair-last? (digits 123445))))
    (is (= false (has-pair-last? (digits 122222))))
    (is (= true (has-pair-last? (digits 122233))))
    (is (= true (has-pair-last? (digits 123455))))
    (is (= false (has-pair-last? (digits 111234))))
    (is (= true (has-pair-last? (digits 112333))))
    (is (= true (has-pair-last? (digits 112345))))
    (is (= true (has-pair-last? (digits 112233))))
    (is (= false (has-pair-last? (digits 123444))))
    (is (= true (has-pair-last? (digits 111223))))
    (is (= true (has-pair-last? (digits 111122))))

    (is (= 1306
           (solve-2 "resources/inputs/2019/04.txt") ))))
