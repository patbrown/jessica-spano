(ns baby.pat.jes.vt.util.chron
  (:require [clojure.string]))


  (defn create-chron-for [m]
    (let [{:keys [minute hour dom month dow]} (merge {:minute "*"
                                                      :hour "*"
                                                      :dom "*"
                                                      :month "*"
                                                      :dow "*"} m)
          convert-to-chron-portion (fn [v] (if (or (string? v) (number? v))
                                             v
                                             (let [lookup {:any (fn [v] "*")
                                                           :specified (fn [v] (if (number? v)
                                                                                v
                                                                                (clojure.string/join "," v)))
                                                           :range (fn [v] (clojure.string/join "-" v))
                                                           :step (fn [v] (str "*/" v))}
                                                   action (if (vector? v)
                                                            (first v)
                                                            v)
                                                   instructions (when (vector? v) (rest v))]
                                               (if instructions
                                                 (let [f (get lookup action)]
                                                   (if (= action :step)
                                                     (f (first instructions))
                                                     (f instructions)))
                                                 (get lookup action)))))
          converted (map convert-to-chron-portion [minute hour dom month dow])]
      (clojure.string/join " " converted)))

  ;; (create-chron-for {:minute 15
  ;;                    :hour 14
  ;;                    :dom 1})

  ;; ;; m-f, every other minute
  ;; (create-chron-for {:minute [:step 2]
  ;;                    :dow [:range 1 5]})

  ;; (create-chron-for {:minute [:step 2]
  ;;                    :dow [:range 1 5]})


