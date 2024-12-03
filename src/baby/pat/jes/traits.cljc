(ns baby.pat.jes.traits
  (:require [baby.pat.jes.tags :as tag]))

(def id {:attr/id   :trait/id
         :attr/card :card/one
         :attr/vt   :vt/qkw})

(def trait {:attr/id   :trait/trait
            :attr/card :card/one
            :attr/vt   :vt/?})

(defn traits-for [nmsp] {:attr/id   (keyword nmsp "traits")
                         :attr/card :card/many
                         :attr/vt   :vt/ident})

(def *traits (traits-for "trait"))
(def tags (tag/tags-for "trait"))

(def attrs [id trait *traits tags])

(def dt {:dt/id       :dt/trait
         :dt/parent   [:dt/id :dt/prim]
         :dt/attrs    attrs})

(defn as-trait [s] {:trait/id (keyword "trait" s) :trait/trait true})

(def is-cloneable? (as-trait "is-cloneable?"))
(def is-dispatchable? (as-trait "is-dispatchable?"))
(def is-unfoldable? (as-trait "is-unfoldable?"))
(def is-invertable? (as-trait "is-invertable?"))
(def needs-tx-added? (as-trait "needs-tx-added?"))
(def needs-created-at-added? (as-trait "needs-created-at-added?"))

(def traits [is-cloneable? is-dispatchable? is-unfoldable? needs-tx-added? needs-created-at-added?])
