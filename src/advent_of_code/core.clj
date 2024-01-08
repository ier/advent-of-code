(ns advent-of-code.core
  (:require
   [gnuplot.core :as gpc]))

(defn plot
  "https://github.com/aphyr/gnuplot/issues/6"
  ([]
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
  ([m]
   (let [rows-data (:rows-data m)]
     (gpc/raw-plot!
      [[:set :title (:title m)]
       [:plot (gpc/range (->> m :range min) (->> m :range max))
        (gpc/list ["-" :title "a" :with :lines]
                  ["-" :title "b" :with :lines]
                  ["-" :title "x" :with :points])]]
      rows-data))))

(comment
  (plot)
  )
