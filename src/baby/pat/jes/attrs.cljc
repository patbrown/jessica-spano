(ns baby.pat.jes.attrs
  (:require [baby.pat.jes.dts :as dt]
            [baby.pat.jes.tags :as tag]
            [baby.pat.jes.traits :as trait]))

(def id {:attr/id   :attr/id
         :attr/card :card/one
         :attr/vt   :vt/qkw})

(def card {:attr/id   :attr/card
           :attr/card :card/one
           :attr/vt   :vt/card})

(def vt {:attr/id   :attr/vt
         :attr/card :card/one
         :attr/vt   :vt/vt-id})

(def tags (tag/tags-for "attr"))
(def traits (trait/traits-for "attr"))

(def attrs [id card vt tags traits])

(def dt {:dt/id       :dt/attr
         :dt/parent   dt/dt
         :dt/attrs    attrs})
