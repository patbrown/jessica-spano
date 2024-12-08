(ns baby.pat.jes.attr
  (:require [baby.pat.jes.card :as card]
            [baby.pat.jes.tag :as tag]
            [baby.pat.jes.trait :as trait]
            [baby.pat.jes.vt :as vt]))

(def id {::id   ::id
         ::card card/one
         ::vt   ::vt/qkw})

(def card {::id   ::card
           ::card card/one
           ::vt   ::vt/card})

(def vt {::id   ::vt
         ::card card/one
         ::vt   ::vt/vt-id})

(def tags (tag/tags-for "attr"))
(def traits (trait/traits-for "attr"))

(def attrs [id card vt tags traits])

(def dt {:baby.pat.jes.dt/id       :baby.pat.jes.dt/attr
         :baby.pat.jes.dt/parent   [:baby.pat.jes.dt/id :baby.pat.jes.dt/dt ]
         :baby.pat.jes.dt/attrs    attrs})
