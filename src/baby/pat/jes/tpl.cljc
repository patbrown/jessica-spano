(ns baby.pat.jes.tpl
  (:require [baby.pat.jes.attr :as attr]
            [baby.pat.jes.card :as card]
            [baby.pat.jes.dt :as dt]
            [baby.pat.jes.prim :refer [as-prim]]
            [baby.pat.jes.vt :as vt]))

(def tpl (update-in (as-prim "tpl" ::vt/str)
                    [::dt/attrs] conj {::attr/id ::vars
                                       ::attr/card card/many
                                       ::attr/vt ::vt/idents}))

