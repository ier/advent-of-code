(ns advent-of-code.07-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [advent-of-code.2020.07 :refer [solve]]))


(deftest parse-line-test
  (testing "parse-line"
    (is (= ["light red" 
            '({:amount "2"
               :title "clear indigo"} 
              {:amount "3"
               :title "light lime"})
            "12"]
           (#'advent-of-code.2020.07/parse-line
            "light red bags contain 2 clear indigo bags, 3 light lime bags.:12"))))
  
  (testing "solve example data"
    (is (= 4
           (solve "resources/inputs/2020/07-test-sample.txt" "shiny gold"))))
  
  #_(testing "solve example data extra"
    (is (= 1
           (solve "resources/inputs/2020/07-1-test-sample.txt" "shiny gold")))))
