(ns advent-of-code.2020.07-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [advent-of-code.2020.07 :refer [solve]]))


(deftest parse-line-test
  (testing "parse-line"
    (is (= ["light red" '({:title "clear indigo" :amount "2"}
                          {:title "light lime" :amount "3"})]
           (#'advent-of-code.2020.07/parse-line
            "light red bags contain 2 clear indigo bags, 3 light lime bags."))))

  (testing "solve example data"
    (is (= 4
           (solve "resources/inputs/2020/07-test-sample.txt" "shiny gold"))))
  
  (testing "solve 07 part 1"
    (is (= 139
           (solve "resources/inputs/2020/07.txt" "shiny gold")))))
