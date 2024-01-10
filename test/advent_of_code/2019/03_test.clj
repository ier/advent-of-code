(ns advent-of-code.2019.03-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [advent-of-code.2019.03 :refer [closest-intersection-distance
                                   solve-1
                                   fewest-combined-steps
                                   solve-2]]))

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
  (is (= 30
         (fewest-combined-steps
          ["U7,R6,D4,L4"
           "R8,U5,L5,D3"])))

  (is (= 610
         (fewest-combined-steps
          ["R75,D30,R83,U83,L12,D49,R71,U7,L72"
           "U62,R66,U55,R34,D71,R55,D58,R83"])))

  (is (= 410
         (fewest-combined-steps
          ["R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"
           "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"])))

  (is (= 30
         (fewest-combined-steps
          ["U7,R6,D3,L2,U3,R2,D4,L4"
           "R8,U5,L5,D3"]) ) )

  (is (= 30
         (fewest-combined-steps
          ["U6,L2,D2,R2,U3,R6,D4,L4"
           "R8,U5,L5,D3"]) ) )

    #_(is (= 42
           (solve-2 "resources/inputs/2019/03.txt"))))
