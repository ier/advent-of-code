(ns advent-of-code.2019.03-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [advent-of-code.2019.03 :refer [closest-intersection-distance solve-1 solve-2]]))

(deftest solve-2019-03-01-test
  (testing "solve-2019-03-test"
    (is (= 159
           (closest-intersection-distance
            ["R75,D30,R83,U83,L12,D49,R71,U7,L72"
             "U62,R66,U55,R34,D71,R55,D58,R83"])))

    (is (= 135
           (closest-intersection-distance
            ["R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"
             "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"])))

    (is (= 207
           (solve-1 "resources/inputs/2019/03.txt")))))

(deftest solve-2019-03-02-test
  (is (= 21196
         (solve-2 "resources/inputs/2019/03.txt"))))
