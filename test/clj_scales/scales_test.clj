(ns clj-scales.scales-test
  (:require [clojure.test :refer :all]
            [clj-scales.scales :refer :all]))

(deftest scale-types
  (testing "major-scales"
    (is (= (major :C) '(:C :D :E :F :G :A :B)))
    (is (= (major :B) '(:B :C# :D# :E :F# :G# :A#)))
    (is (= (major :F) '(:F :G :A :Bb :C :D :E)))
    (is (= (major :Db) '(:Db :Eb :F :Gb :Ab :Bb :C)))
    (is (= (major :C#) '(:C# :D# :E# :F# :G# :A# :B#))))
  (testing "minor-scales"
    (is (= (minor :C) '(:C :D :Eb :F :G :Ab :Bb)))
    (is (= (minor :B) '(:B :C# :D :E :F# :G :A)))
    (is (= (minor :F) '(:F :G :Ab :Bb :C :Db :Eb)))
    (is (= (minor :Db) '(:Db :Eb :Fb :Gb :Ab :Bbb :Cb)))
    (is (= (minor :C#) '(:C# :D# :E :F# :G# :A :B)))))
