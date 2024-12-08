(ns baby.pat.jes.entity
  (:require [baby.pat.jes.attr :as attr]
            [baby.pat.jes.card :as card]
            [baby.pat.jes.dt :as dt]
            [baby.pat.jes.tag :as tag]
            [baby.pat.jes.trait :as trait]
            [baby.pat.jes.vt :as vt]))

(def id {::attr/id   ::id
         ::attr/card card/one
         ::attr/vt   ::vt/str})

(def *dt {::attr/id   ::dt
          ::attr/card card/one
          ::attr/vt   ::vt/ident})

(def created-at {::attr/id   ::created-at
                 ::attr/card card/one
                 ::attr/vt   ::vt/long})

(def ident {::attr/id ::ident
            ::attr/card card/one
            ::attr/vt ::vt/indent})

(def entity {::attr/id ::entity
             ::attr/card card/one
             ::attr/vt ::vt/map})

(def tags (tag/tags-for "entity"))
(def traits (trait/traits-for "entity"))


(def attrs [id *dt created-at ident entity tags traits])

(def dt {::dt/id       ::dt/entity
         ::dt/parent   dt/dt
         ::dt/attrs    attrs
         ::dt/traits [trait/needs-created-at-added?]})
