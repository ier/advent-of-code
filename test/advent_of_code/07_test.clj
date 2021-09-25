(ns advent-of-code.07-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [advent-of-code.2020.07 :as d07]))


(deftest parse-line-test
  (testing "FIXME, I fail."
    (is (= ["light red" 
            '({:amount "2"
               :title "clear indigo"} 
              {:amount "3"
               :title "light lime"})]
           (#'advent-of-code.2020.07/parse-line
            "light red bags contain 2 clear indigo bags, 3 light lime bags.")))))
