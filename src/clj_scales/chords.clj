(ns clj-scales.chords
  (:require [clj-scales.scales :as scales]
            [clj-scales.notes :as notes]
            [net.cgrand.regex :as regex]))

; In order to properly generate these chords, we use some tricks to get them
; scale modes where they are the tonic triad.
(defn- tonic-chord-from-scale
  [scale]
  (list (nth scale 0) (nth scale 2) (nth scale 4)))

(defn major
  "Returns the notes of the major chord of note."

  [note]

  (tonic-chord-from-scale (scales/major note)))

(defn minor
  "Returns the notes of the minor chord of note."

  [note]
  (assert (.contains notes/notes note))

  (tonic-chord-from-scale (scales/minor note)))

(defn diminished
  "Returns the notes of the diminished chord of note."

  [note]
  (assert (.contains notes/notes note))

  (tonic-chord-from-scale (scales/locrian note)))

; This is a special case because augmented tonic triads do not occur in western
; modes.
(defn augmented
  "Returns the notes of the augmented chord of note."

  [note]
  (assert (.contains notes/notes note))

  (let [major-chord (tonic-chord-from-scale (scales/major note))
        old-fifth (nth major-chord 2)
        new-fifth (notes/equivalent-from-notename
                    (notes/note+ old-fifth 0.5)
                    (notes/notename-from-note old-fifth))]
    (concat (take 2 major-chord) (list new-fifth))))

(defn add-6th
  "Returns a chord that corresponds to chord with a 6th added."
  [chord]
  (concat chord (list (nth (scales/major (first chord)) 5))))

(defn add-7th
  "Returns a chord that corresponds to chord with a major 7th added."

  [chord]
  (concat chord (list (nth (scales/major (first chord)) 6))))

(defn add-min-7th
  "Returns a chord that corresponds to chord with a minor 7th added."
  [chord]
  (let [seventh (nth (scales/major (first chord)) 6)
        seventh-name (notes/notename-from-note seventh)
        minor-seventh (notes/equivalent-from-notename
                        (notes/note- seventh 0.5) seventh-name)]
  (concat chord (list minor-seventh))))

(defn add-dim-7th
  "Returns a chord that corresponds to chord with a diminished 7th added.
  This is = add-6th."
  [chord]
  (let [seventh (nth (scales/major (first chord)) 6)
        seventh-name (notes/notename-from-note seventh)
        dim-seventh (notes/equivalent-from-notename
                        (notes/note- seventh 1) seventh-name)]
  (concat chord (list dim-seventh))))

(defn major-seventh
  "Returns the major seventh chord for note."
  [note]
  (add-7th (major note)))

(defn minor-seventh
  "Returns the minor seventh chord for note."
  [note]
  (add-min-7th (minor note)))

(defn dominant-seventh
  "Returns the dominant seventh chord for note."
  [note]
  (add-min-7th (major note)))

(defn augmented-seventh
  "Returns the augmented seventh chord for note."
  [note]
  (add-min-7th (augmented note)))

(defn diminished-seventh
  "Returns the diminished seventh chord for note."
  [note]
  (add-dim-7th (diminished note)))

(def chord-string-re
  (let [note {\a \g}
        accidental #{\b \#}
        type-chord #{"maj" "m" "min" "aug" "dim" "dom"}
        added #{\6 \7}]
    (regex/regex [note (regex/repeat accidental 0 1) :as :note]
                 [(regex/repeat type-chord 0 1) :as :type]
                 [(regex/repeat added) :as :added])))

(defn chord-string->chord
  ; Returns a chord that corresponds to the string.
  [string]

  (let [parsed (regex/exec chord-string-re (clojure.string/lower-case string))
        root (keyword (clojure.string/upper-case (:note parsed)))
        chord-type (:type parsed)]
    (assert (.contains notes/notes root) "Root note is not a note.")

    (let [chord (case chord-type
                  "m"   (minor root)
                  "min" (minor root)
                  ""    (major root)
                  "dom" (major root)
                  "maj" (major root)
                  "dim" (diminished root)
                  "aug" (augmented root))]

      (case (:added parsed)
        "" chord
        "6" (add-6th chord)
        "7" (case chord-type
              "maj" (add-7th chord)
              "m"   (add-min-7th chord)
              "min" (add-min-7th chord)
              ""    (add-min-7th chord)
              "dom" (add-min-7th chord)
              "dim" (add-dim-7th chord)
              "aug" (add-min-7th chord)))))) ;TODO test this case

(defn chord->chord-string
  ; Returns the string representation of chord.
  [chord]
  (assert (and (>= (count chord) 3) (<= (count chord) 4))
          "Chord does not have 3 or 4 notes.")

  (str (name (first chord))
    (let [interval-2nd (notes/intervalBetween (first chord) (second chord))
          interval-3rd (notes/intervalBetween (first chord) (nth chord 2))
          interval-4th (if (nth chord 3 nil)
                           (notes/intervalBetween (first chord) (nth chord 3)))]
      (str
        (cond
          ; evaluted before maj because it's a special case of it
          (and (= interval-2nd 2.0) (= interval-3rd 3.5)
               (= interval-4th 5.0))                      "dom"
          (and (= interval-2nd 2.0) (= interval-3rd 3.5)) "maj"
          (and (= interval-2nd 1.5) (= interval-3rd 3.5)) "min"
          (and (= interval-2nd 1.5) (= interval-3rd 3.0)) "dim"
          (and (= interval-2nd 2.0) (= interval-3rd 4.0)) "aug")

        (if interval-4th
            (if (and (= interval-4th 4.5)
                 ; only a 6th when the interval is 4.5 and the chord is not dim
                (not (and (= interval-2nd 1.5) (= interval-3rd 3.0))))
                6
                7))))))
