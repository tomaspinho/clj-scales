(ns clj-scales.chords-test
  (:require [clojure.test :refer :all]
            [clj-scales.chords :refer :all]))

(deftest chord-types
  (testing "major-chords"
    (is (= (major 'C) '(C E G)))
    (is (= (major 'B) '(B D# F#)))
    (is (= (major 'C#) '(C# F G#))))

  (testing "minor-chords"
    (is (= (minor 'C) '(C D# G)))
    (is (= (minor 'B) '(B D F#)))
    (is (= (minor 'C#) '(C# E G#))))

  (testing "diminished-chords"
    (is (= (diminished 'C) '(C D# F#)))
    (is (= (diminished 'B) '(B D F)))
    (is (= (diminished 'C#) '(C# E G))))

  (testing "augmented-chords"
    (is (= (augmented 'C) '(C E G#)))
    (is (= (augmented 'B) '(B D# G)))
    (is (= (augmented 'C#) '(C# F A)))))

(deftest chord-modifiers
  (testing "6th"
    (is (= (add-6th (major 'C)) '(C E G A)))
    (is (= (add-6th (major 'B)) '(B D# F# G#)))
    (is (= (add-6th (major 'C#)) '(C# F G# A#))))

  (testing "7th"
    (is (= (add-7th (major 'C)) '(C E G B)))
    (is (= (add-7th (major 'B)) '(B D# F# A#)))
    (is (= (add-7th (major 'C#)) '(C# F G# C)))
    (is (= (add-7th (minor 'C)) '(C D# G B)))
    (is (= (add-7th (minor 'B)) '(B D F# A#)))
    (is (= (add-7th (minor 'C#)) '(C# E G# C))))

  (testing "dom-7th"
    (is (= (add-dom-7th (major 'C)) '(C E G A#)))
    (is (= (add-dom-7th (major 'B)) '(B D# F# A)))
    (is (= (add-dom-7th (major 'C#)) '(C# F G# B))))

  (testing "dim-7th"
    (is (= (add-dim-7th (major 'C)) '(C E G A)))
    (is (= (add-dim-7th (major 'B)) '(B D# F# G#)))
    (is (= (add-dim-7th (major 'C#)) '(C# F G# A#)))))

(deftest string->chord
  (testing "types"
    (is (= (chord-string->chord "C") '(C E G)))
    (is (= (chord-string->chord "B") '(B D# F#)))
    (is (= (chord-string->chord "C#") '(C# F G#)))

    (is (= (chord-string->chord "Cmaj") '(C E G)))
    (is (= (chord-string->chord "Bmaj") '(B D# F#)))
    (is (= (chord-string->chord "C#maj") '(C# F G#)))

    (is (= (chord-string->chord "Cm") '(C D# G)))
    (is (= (chord-string->chord "Cmin") '(C D# G)))
    (is (= (chord-string->chord "Bmin") '(B D F#)))
    (is (= (chord-string->chord "C#min") '(C# E G#)))

    (is (= (chord-string->chord "Cdim") '(C D# F#)))
    (is (= (chord-string->chord "Bdim") '(B D F)))
    (is (= (chord-string->chord "C#dim") '(C# E G))))

    (is (= (chord-string->chord "Caug") '(C E G#)))
    (is (= (chord-string->chord "Baug") '(B D# G)))
    (is (= (chord-string->chord "C#aug") '(C# F A)))

  (testing "added"
    (is (= (chord-string->chord "C6") '(C E G A)))
    (is (= (chord-string->chord "B6") '(B D# F# G#)))
    (is (= (chord-string->chord "C#6") '(C# F G# A#)))

    (is (= (chord-string->chord "Cmaj7") '(C E G B)))
    (is (= (chord-string->chord "Bmaj7") '(B D# F# A#)))
    (is (= (chord-string->chord "C#maj7") '(C# F G# C)))

    (is (= (chord-string->chord "Cm7") '(C D# G A#)))
    (is (= (chord-string->chord "Cmin7") '(C D# G A#)))
    (is (= (chord-string->chord "Bmin7") '(B D F# A)))
    (is (= (chord-string->chord "C#min7") '(C# E G# B))))

    ; dom = without type
    (is (= (chord-string->chord "C7") '(C E G A#)))
    (is (= (chord-string->chord "Cdom7") '(C E G A#)))
    (is (= (chord-string->chord "Bdom7") '(B D# F# A)))
    (is (= (chord-string->chord "C#dom7") '(C# F G# B)))

    (is (= (chord-string->chord "Cdim7") '(C D# F# A)))
    (is (= (chord-string->chord "Bdim7") '(B D F G#)))
    (is (= (chord-string->chord "C#dim7") '(C# E G A#))))

(deftest chord->string
  (testing "types"
    (is (= (chord->chord-string '(C E G)) "Cmaj"))
    (is (= (chord->chord-string '(B D# F#)) "Bmaj"))
    (is (= (chord->chord-string '(C# F G#)) "C#maj"))

    (is (= (chord->chord-string '(C D# G)) "Cmin"))
    (is (= (chord->chord-string '(B D F#)) "Bmin"))
    (is (= (chord->chord-string '(C# E G#)) "C#min"))

    (is (= (chord->chord-string '(C D# F#)) "Cdim"))
    (is (= (chord->chord-string '(B D F)) "Bdim"))
    (is (= (chord->chord-string '(C# E G)) "C#dim"))

    (is (= (chord->chord-string '(C E G#)) "Caug"))
    (is (= (chord->chord-string '(B D# G)) "Baug"))
    (is (= (chord->chord-string '(C# F A)) "C#aug"))

  (testing "added"
    (is (= (chord->chord-string '(C E G A)) "Cmaj6"))
    (is (= (chord->chord-string '(B D# F# G#)) "Bmaj6"))
    (is (= (chord->chord-string '(C# F G# A#)) "C#maj6"))

    (is (= (chord->chord-string '(C E G B)) "Cmaj7"))
    (is (= (chord->chord-string '(B D# F# A#)) "Bmaj7"))
    (is (= (chord->chord-string '(C# F G# C)) "C#maj7"))

    (is (= (chord->chord-string '(C D# G A#)) "Cmin7"))
    (is (= (chord->chord-string '(B D F# A)) "Bmin7"))
    (is (= (chord->chord-string '(C# E G# B)) "C#min7"))

    (is (= (chord->chord-string '(C E G A#)) "Cdom7"))
    (is (= (chord->chord-string '(B D# F# A)) "Bdom7"))
    (is (= (chord->chord-string '(C# F G# B)) "C#dom7"))

    (is (= (chord->chord-string '(C D# F# A)) "Cdim7"))
    (is (= (chord->chord-string '(B D F G#)) "Bdim7"))
    (is (= (chord->chord-string '(C# E G A#)) "C#dim7")))))
