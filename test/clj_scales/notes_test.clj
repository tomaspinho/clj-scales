(ns clj-scales.notes-test
  (:require [clojure.test :refer :all]
            [clj-scales.notes :refer :all]))

(deftest intervals
  (testing "Intervals"
    (testing "with y > x"
      (is (= (intervalBetween :C :D) 1.0))
      (is (= (intervalBetween :C :E) 2.0))
      (is (= (intervalBetween :F :G) 1.0))
      (is (= (intervalBetween :F# :G) 0.5))
      (is (= (intervalBetween :C :F) 2.5))

    (testing "with x > y"
      (is (= (intervalBetween :A# :C) 1.0))
      (is (= (intervalBetween :A :C) 1.5))
      (is (= (intervalBetween :B :C) 0.5))))))

(deftest plus
  (testing "Plus operator"
    (testing "with positive operand"
      (is (= (note+ :C 1) :D))
      (is (= (note+ :C 2) :E))
      (is (= (note+ :C 2.5) :F))
      (is (= (note+ :B 0.5) :C))
      (is (= (note+ :B 1) :C#))
      (is (= (note+ :C 6) :C))
      (is (= (note+ :C 7) :D)))
    (testing "with negative operand"
      (is (= (note+ :C -1) :A#))
      (is (= (note+ :C -2) :G#))
      (is (= (note+ :C -2.5) :G))
      (is (= (note+ :B -0.5) :A#))
      (is (= (note+ :B -1) :A))
      (is (= (note+ :C -6) :C))
      (is (= (note+ :C -6.5) :B)))))


(deftest minus
  (testing "Minus operator"
    (testing "with positive operand"
      (is (= (note- :C 1) :A#))
      (is (= (note- :C 2) :G#))
      (is (= (note- :C 2.5) :G))
      (is (= (note- :B 0.5) :A#))
      (is (= (note- :B 1) :A))
      (is (= (note- :C 6) :C))
      (is (= (note- :C 6.5) :B)))
    (testing "with negative operand"
      (is (= (note- :C -1) :D))
      (is (= (note- :C -2) :E))
      (is (= (note- :C -2.5) :F))
      (is (= (note- :B -0.5) :C))
      (is (= (note- :B -1) :C#))
      (is (= (note- :C -6) :C))
      (is (= (note- :C -7) :D)))))
