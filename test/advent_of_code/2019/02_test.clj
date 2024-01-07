(ns advent-of-code.2019.02-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [advent-of-code.2019.02 :refer [solve-1 parse init run]]))

(deftest solve-2019-01-test
  (testing "solve-2019-01-test"
    (is (= 3267740
           (solve-1 "resources/inputs/2019/02.txt")))))

(deftest solve-2019-02-test
  (testing "solve-2019-02-test"
    (is (= 19690720
           (let [noun 78
                 verb 70]
             (-> "resources/inputs/2019/02.txt"
                 parse
                 (init noun verb)
                 run))))))
