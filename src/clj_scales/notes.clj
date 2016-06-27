(ns clj-scales.notes)

(def notes [:C :C# :D :D# :E :F :F# :G :G# :A :A# :B])

(defn intervalBetween

  "Returns the number of tones between note x and note y. If y < x, returns the
  number of tones between x and the next octave's y."

  [x y]
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
    )))

(defn note+

  "Returns the result of adding x tones to the note."

  [note x]

  (assert (.contains notes note) "Note is not valid.")

  (let [posNote (.indexOf notes note)
        posInc (/ x 0.5)
        numNotes (count notes)
        finalPos (int (rem (+ posNote posInc) numNotes))]

    (if (< finalPos 0)
      (notes (+ numNotes finalPos))
      (notes finalPos)
      )))

(defn note-

  "Returns the result of subtracting x tones from the note."

  [note x]
  (note+ note (- x)))

(defn equivalent

  "Returns the equivalent note in our vector of given note.
  e.g. Bb = A#; F## = G."

  [note]

  (let [note (name note)]
    (note+ )

  ))
