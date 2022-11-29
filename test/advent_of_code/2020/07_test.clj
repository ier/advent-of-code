(ns advent-of-code.2020.07-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [advent-of-code.2020.07 :refer [solve-1 solve-2]]))


(deftest parse-line-test
  (testing "parse-line"
    (is (= ["light red" '({:title "clear indigo" :amount 2}
                          {:title "light lime" :amount 3})]
           (#'advent-of-code.2020.07/parse-line
            "light red bags contain 2 clear indigo bags, 3 light lime bags.")))))

(deftest solve-1-test
  (testing "solve 07 part 1"
    (is (= 4
           (solve-1 "resources/inputs/2020/07-1-test-sample.txt" "shiny gold")))
    (is (= 139
           (solve-1 "resources/inputs/2020/07.txt" "shiny gold")))))

(deftest solve-2-test
  (testing "solve 07 part 2"
    (is (= 126
           (solve-2 "resources/inputs/2020/07-2-test-sample.txt" "shiny gold")))
    #_(is (= _
           (solve-2 "resources/inputs/2020/07.txt" "shiny gold")))))
