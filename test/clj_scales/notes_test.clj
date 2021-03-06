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

(deftest equivalent-test
  (testing "flats"
    (is (= (equivalent :Ab) :G#))
    (is (= (equivalent :Abb) :G))
    (is (= (equivalent :Abbb) :F#))
    (is (= (equivalent :Cb) :B)))
  (testing "sharps"
    (is (= (equivalent :G#) :G#))
    (is (= (equivalent :G##) :A))
    (is (= (equivalent :G###) :A#))
    (is (= (equivalent :B#) :C))))

(deftest equivalent-from-notename-test
  (testing "flats"
    (is (= (equivalent-from-notename :A :B) :Bbb))
    (is (= (equivalent-from-notename :Abb :B) :Bbbbb))
    (is (= (equivalent-from-notename :Abbb :B) :Bbbbbb))
    (is (= (equivalent-from-notename :Cb :B) :B))
    (is (= (equivalent-from-notename :B :C) :Cb)))
  (testing "sharps"
    (is (= (equivalent-from-notename :G# :F) :F###))
    (is (= (equivalent-from-notename :G## :A) :A))
    (is (= (equivalent-from-notename :G### :A) :A#))
    (is (= (equivalent-from-notename :C :B) :B#))))
