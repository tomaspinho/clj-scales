(ns clj-scales.scales
  (:require [clj-scales.notes :as notes]))


(def c-major-scale-notes [:C :D :E :F :G :A :B])

;; The following could have been generated programatically, but this is good
;; for self documentation of the library.
(def major-intervals      [ 1   1   0.5 1   1   1   0.5 ])
(def dorian-intervals     [ 1   0.5 1   1   1   0.5 1	  ])
(def phrygian-intervals   [ 0.5 1   1   1   0.5 1   1   ])
(def lydian-intervals     [ 1   1   1   0.5 1   1   0.5 ])
(def mixolydian-intervals [ 1   1   0.5 1   1   0.5 1   ])
(def minor-intervals      [ 1   0.5 1   1   0.5 1   1   ])
(def locryan-intervals    [ 0.5 1   1   0.5 1   1   1   ])

(defn apply-intervals
  ; TODO take care of offset for root note
  ([note intervals]
   (let [ note-name (-> note
                        name
                        first
                        str
                        keyword)
          index-note (.indexOf c-major-scale-notes note-name)
          split-scale (split-at index-note c-major-scale-notes)
          model-scale (concat (split-scale 1) (split-scale 0)) ]

     (apply-intervals note intervals 0 model-scale (list note))))

  ([note intervals accinterval model-scale result]
   (let [ to       (second model-scale)
          interval (when (not (nil? to)) (+ (first intervals) accinterval))
          difference (when (not (nil? to)) (- (notes/intervalBetween note to) interval))]

     (if (nil? to)
         result
         (let [ res-note (notes/note- to difference)
                corrected-res (notes/equivalent-from-notename res-note to) ]

           (apply-intervals note
                            (rest intervals)
                            interval
                            (rest model-scale)
                            (concat result (list corrected-res))))))))

(defn major [note] (apply-intervals note major-intervals))
(defn ionian major)
(defn dorian [note] (apply-intervals note dorian-intervals))
(defn phrygian [note] (apply-intervals note phrygian-intervals))
(defn lydian [note] (apply-intervals note lydian-intervals))
(defn mixolydian [note] (apply-intervals note mixolydian-intervals))
(defn minor [note] (apply-intervals note minor-intervals))
(defn aeolian minor)
(defn locryan [note] (apply-intervals note locryan-intervals))
