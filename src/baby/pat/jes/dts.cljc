(ns baby.pat.jes.dts
  (:require [baby.pat.jes.traits :as trait]))

(def id {:attr/id   :dt/id
         :attr/card :card/one
         :attr/vt   :vt/dt-id})

(def parent {:attr/id   :dt/parent
             :attr/card :card/one
             :attr/vt   :vt/dt-ident})

(def *attrs {:attr/id   :dt/attrs
            :attr/card :card/many
            :attr/vt   :vt/attr-idents})

(def tags {:attr/id :dt/tags, :attr/card :card/many, :attr/vt :vt/ident})
(def traits (trait/traits-for "dt"))

(def attrs [id parent *attrs tags traits])

(def dt {:dt/id       :dt/dt
         :dt/parent   [:dt/id :dt/dt]
         :dt/attrs    attrs
         :dt/traits   [trait/is-cloneable?]})
