(ns advent-of-code.core
  (:require
   [gnuplot.core :as gpc]))

(defn plot
  "https://github.com/aphyr/gnuplot/issues/6"
  []
  (gpc/raw-plot!
   [[:set :title "simple-test"]
    [:plot (gpc/range 0 5)
     (gpc/list
      ["-" :title "rising" :with :lines]
      ["-" :title "falling" :with :impulse])]]
   [[[0 0]
     [1 1]
     [2 2]
     [3 1]
     [4 3]
     [5 4]]
    [[0 5]
     [1 4]
     [2 3]
     [3 2]
     [4 1]
     [5 0]]]))

(comment
  (plot)
  )

(comment
  #_
  (let [{:keys [traces points result]}
        (fewest-combined-steps
         ["R75,D30,R83,U83,L12,D49,R71,U7,L72"
          "U62,R66,U55,R34,D71,R55,D58,R83"])
       lines (vec traces)
       dots (vec points)]
   (gpc/raw-plot!
    [[:set :title "simple-test"]
     [:plot (gpc/range -50 300)
      (gpc/list ["-" :title "a" :with :lines]
                ["-" :title "b" :with :lines]
                ["-" :title "x" :with :points])]]
    (conj lines dots))
   result)
  )
