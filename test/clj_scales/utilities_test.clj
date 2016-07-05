(ns clj-scales.utilities-test
  (:require [clojure.test :refer :all]
            [clj-scales.scales :refer :all]
            [clj-scales.utilities :refer :all]))

(deftest test-chords-in-scale
  (testing "major-scales"
    (is (= (chords-in-scale (major :C))
           '{:I (:C :E :G) :II (:D :F :A) :III (:E :G :B)
             :IV (:F :A :C) :V (:G :B :D) :VI (:A :C :E)
             :VII (:B :D :F)}))

    (is (= (chords-in-scale (major :B))
           '{:I (:B :D# :F#) :II (:C# :E :G#) :III (:D# :F# :A#)
             :IV (:E :G# :B) :V (:F# :A# :C#) :VI (:G# :B :D#)
             :VII (:A# :C# :E)}))

    (is (= (chords-in-scale (major :F))
           '{:I (:F :A :C) :II (:G :Bb :D) :III (:A :C :E)
             :IV (:Bb :D :F) :V (:C :E :G) :VI (:D :F :A)
             :VII (:E :G :Bb)})))

  (testing "minor-scales"
    (is (= (chords-in-scale (minor :C))
           '{:I (:C :Eb :G) :II (:D :F :Ab) :III (:Eb :G :Bb)
             :IV (:F :Ab :C) :V (:G :Bb :D) :VI (:Ab :C :Eb)
             :VII (:Bb :D :F)}))

    (is (= (chords-in-scale (minor :B))
           '{:I (:B :D :F#) :II (:C# :E :G) :III (:D :F# :A)
             :IV (:E :G :B) :V (:F# :A :C#) :VI (:G :B :D)
             :VII (:A :C# :E)}))

    (is (= (chords-in-scale (minor :F))
           '{:I (:F :Ab :C) :II (:G :Bb :Db) :III (:Ab :C :Eb)
             :IV (:Bb :Db :F) :V (:C :Eb :G) :VI (:Db :F :Ab)
             :VII (:Eb :G :Bb)}))))

(deftest test-chord-names-in-scale
  (testing "major-scales"
    (is (= (chordnames-in-scale (major :C))
           '{:I "Cmaj" :II "Dmin" :III "Emin" :IV "Fmaj" :V "Gmaj"
             :VI "Amin" :VII "Bdim"}))

    (is (= (chordnames-in-scale (major :B))
            '{:I "Bmaj" :II "C#min" :III "D#min" :IV "Emaj" :V "F#maj"
             :VI "G#min" :VII "A#dim"}))

    (is (= (chordnames-in-scale (major :F))
           '{:I "Fmaj" :II "Gmin" :III "Amin" :IV "Bbmaj" :V "Cmaj"
             :VI "Dmin" :VII "Edim"})))

  (testing "minor-scales"
    (is (= (chordnames-in-scale (minor :C))
           '{:I "Cmin"	:II "Ddim" :III "Ebmaj" :IV "Fmin" :V "Gmin"
             :VI "Abmaj" :VII "Bbmaj"}))

    (is (= (chordnames-in-scale (minor :B))
           '{:I "Bmin"	:II "C#dim" :III "Dmaj" :IV "Emin" :V "F#min"
             :VI "Gmaj" :VII "Amaj"}))

    (is (= (chordnames-in-scale (minor :F))
           '{:I "Fmin"	:II "Gdim" :III "Abmaj" :IV "Bbmin" :V "Cmin"
             :VI "Dbmaj" :VII "Ebmaj"}))))
