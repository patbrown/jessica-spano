(ns baby.pat.jes.trait
  (:require [baby.pat.jes.card :as card]
            [baby.pat.jes.vt :as vt]
            [baby.pat.jes.tag :as tag]))

(def id {:baby.pat.jes.attr/id   ::id
         :baby.pat.jes.attr/card card/one
         :baby.pat.jes.attr/vt   ::vt/qkw})

(def trait {:baby.pat.jes.attr/id   ::trait
            :baby.pat.jes.attr/card card/one
            :baby.pat.jes.attr/vt   ::vt/?})

(defn traits-for [nmsp] {:baby.pat.jes.attr/id   (keyword nmsp "traits")
                         :baby.pat.jes.attr/card card/many
                         :baby.pat.jes.attr/vt   ::vt/ident})

(def *traits (traits-for "trait"))
(def tags (tag/tags-for "trait"))

(def attrs [id trait *traits tags])

(def dt {:baby.pat.jes.dt/id       :baby.pat.jes.dt/trait
         :baby.pat.jes.dt/parent   [:baby.pat.jes.dt/id :baby.pat.jes.dt/prim]
         :baby.pat.jes.dt/attrs    attrs})

(defn as-trait [s] {::id (keyword (namespace ::ignored) s) ::trait true})

(def is-cloneable? (as-trait "is-cloneable?"))
(def is-dispatchable? (as-trait "is-dispatchable?"))
(def is-unfoldable? (as-trait "is-unfoldable?"))
(def is-invertable? (as-trait "is-invertable?"))
(def needs-tx-added? (as-trait "needs-tx-added?"))
(def needs-created-at-added? (as-trait "needs-created-at-added?"))

(def traits [is-cloneable? is-dispatchable? is-unfoldable? needs-tx-added? needs-created-at-added?])
