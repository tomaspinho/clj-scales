(ns clj-scales.utilities
  (:require [clj-scales.scales :as scales]
            [clj-scales.chords :as chords]))


(defn update-values

 ; Updates values in a map m by applying f to them.

 [m f & args]

 (reduce (fn [r [k v]] (assoc r k (apply f v args))) {} m))

(defn chords-in-scale

  ; Returns all chords in a scale.

  [scale]

  (loop [roots scale
         result {}
         index 1]

    (if (empty? roots)
        result
        (let [root (first roots)
              iroot (.indexOf scale root)
              chord (list root
                          (nth scale (rem (+ iroot 2) (count scale)))
                          (nth scale (rem (+ iroot 4) (count scale))))]

          (recur (rest roots)
                 (assoc result index chord)
                 (inc index))))))

(defn chordnames-in-scale

  ; Returns all chordnames in a scale

  [scale]

  (update-values (chords-in-scale scale) chords/chord->chord-string))
