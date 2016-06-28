(ns clj-scales.scales
  (:require [clj-scales.notes :as notes]))


(def c-major-scale-notes [:C :D :E :F :G :A :B])

(def major-intervals [1 1 0.5 1 1 1 0.5])
(def minor-intervals [1 0.5 1 1 0.5 1 1])

(defn apply-intervals
  [note intervals]


)

(defn major [note] (apply-intervals note major-intervals))
(defn minor [note] (apply-intervals note minor-intervals))
