(ns baby.pat.jes.dt
  (:require [baby.pat.jes.attr :as attr]
            [baby.pat.jes.card :as card]
            [baby.pat.jes.trait :as trait]
            [baby.pat.jes.vt :as vt]))

(def id {::attr/id   ::id
         ::attr/card card/one
         ::attr/vt   :vt/dt-id})

(def parent {::attr/id   ::parent
             ::attr/card card/one
             ::attr/vt   :vt/dt-ident})

(def *attrs {::attr/id   ::attrs
             ::attr/card card/many
             ::attr/vt   :vt/idents})

(def tags {::attr/id ::tags, ::attr/card card/many, ::attr/vt :vt/idents})
(def traits (trait/traits-for "dt"))

(def attrs [id parent *attrs tags traits])

(def dt {::id       ::dt
         ::parent   [::id ::dt]
         ::attrs    attrs
         ::traits   [trait/is-cloneable?]})
