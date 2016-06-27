(ns clj-scales.notes)

(def notes [:C :C# :D :D# :E :F :F# :G :G# :A :A# :B])

(declare equivalent)

(defn intervalBetween

  "Returns the number of tones between note x and note y. If y < x, returns the
  number of tones between x and the next octave's y."

  [x y]
  (let [x (equivalent x)
        y (equivalent y)]
    (assert (.contains notes x) (str "First note is not valid: " x))
    (assert (.contains notes y) (str "Second note is not valid: " y))

    (let [posX (.indexOf notes x)
          posY (.indexOf notes y)
          end (dec (count notes))
          end-x (- end posX)]

      (if (< posY posX)
        ; The weird case, when  y > x
        (* (+ (inc end-x) posY) 0.5)
        ; The default case, when y < x
        (* (- posY posX) 0.5)
      ))))

(defn- privnote+

  "Returns the result of adding x tones to the note. Private to fix circular
  dependency with equivalent"

  [note x]


    (assert (.contains notes note) (str "Note is not valid: " note))

    (let [posNote (.indexOf notes note)
          posInc (/ x 0.5)
          numNotes (count notes)
          finalPos (int (rem (+ posNote posInc) numNotes))]

      (if (< finalPos 0)
        (notes (+ numNotes finalPos))
        (notes finalPos)
      )))

(defn note+

  "Returns the result of adding x tones to the note."

  [note x]
  (let [note (equivalent note)]
    (privnote+ note x)))

(defn note-

  "Returns the result of subtracting x tones from the note."

  [note x]
  (note+ note (- x)))

(defn equivalent

  "Returns the equivalent note in our vector of given note.
  e.g. Bb = A#; F## = G."

  [note]

  (let [string (name note)
        note (first string)
        accidentals (rest string)
        value (fn [x]
                (cond
                  (char? x) (cond
                              (= x \#) 0.5
                              (= x \b) -0.5
                              :else (throw (RuntimeException.
                                       (str "equivalent - x is not an "
                                            "acceptable value: " x))))
                  (number? x) x))]

    (privnote+ (keyword (str note))
           (reduce (fn [x y]
                       (+ (value x) (value y))) 0 accidentals))))
