(ns baby.pat.jes.prims
  (:require [baby.pat.jes.dts :as dt]
            [baby.pat.jes.tags :as tag]
            [baby.pat.jes.traits :as trait]))

(def id {:attr/id :prim/id
         :attr/card :card/one
         :attr/vt :vt/qkw})

(def prim {:attr/id :prim/prim
           :attr/card :card/one
           :attr/vt :vt/any})

(def tags (tag/tags-for "prim"))
(def traits (trait/traits-for "prim"))

(def attrs [id prim tags traits])

(def dt {:dt/id       :dt/dt
         :dt/parent   dt/dt
         :dt/attrs    attrs})

(defn as-prim [token vt]
  {:dt/id       (keyword "dt" token)
   :dt/parent   dt
   :dt/attrs    [{:attr/id (keyword token "id")
                  :attr/card :card/one
                  :attr/vt :vt/qkw}
                 {:attr/id (keyword token token)
                  :attr/card :card/one
                  :attr/vt vt}
                 (tag/tags-for token)
                 (trait/traits-for token)]})
