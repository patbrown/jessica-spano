(ns baby.pat.jes.instances
  (:require [baby.pat.jes.dts :as dt]
            [baby.pat.jes.tags :as tag]
            [baby.pat.jes.traits :as trait]))

(def id {:attr/id   :instance/id
         :attr/card :card/one
         :attr/vt   :vt/str})

(def *dt {:attr/id   :instance/dt
         :attr/card :card/one
         :attr/vt   :vt/ident})

(def created-at {:attr/id   :instance/created-at
                 :attr/card :card/one
                 :attr/vt   :vt/long})

(def ident {:attr/id :instance/ident
            :attr/card :card/one
            :attr/vt :vt/indent})

(def instance {:attr/id :instance/instance
               :attr/card :card/one
               :attr/vt :vt/map})

(def tags (tag/tags-for "instance"))
(def traits (trait/traits-for "instance"))


(def attrs [id *dt created-at ident instance tags traits])

(def dt {:dt/id       :dt/instance
         :dt/parent   dt/dt
         :dt/attrs    attrs
         :dt/traits [trait/needs-created-at-added?]})
