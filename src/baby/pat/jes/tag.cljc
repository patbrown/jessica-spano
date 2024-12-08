(ns baby.pat.jes.tag
  (:require [baby.pat.jes.card :as card]
            [baby.pat.jes.vt :as vt]
            [baby.pat.jes.vt.util :as u]))

(def id {:baby.pat.jes.attr/id   ::id
         :baby.pat.jes.attr/card card/one
         :baby.pat.jes.attr/vt   ::vt/str})

(def vt {:baby.pat.jes.attr/id   ::vt
         :baby.pat.jes.attr/card card/one
         :baby.pat.jes.attr/vt   ::vt/vt-id})

(def tag {:baby.pat.jes.attr/id   ::tag
          :baby.pat.jes.attr/card card/one
          :baby.pat.jes.attr/vt   ::vt/any})

(defn tags-for [nmsp] {:baby.pat.jes.attr/id   (keyword nmsp "tags")
                       :baby.pat.jes.attr/card card/many
                       :baby.pat.jes.attr/vt   ::vt/ident})

(def *tags (tags-for "tag"))
(def traits #:baby.pat.jes.attr{:id ::traits, :card card/many, :vt ::vt/ident})

(def attrs [id vt tag *tags traits])

(def dt {:baby.pat.jes.dt/id       :baby.pat.jes.dt/tag
         :baby.pat.jes.dt/parent   [:baby.pat.jes.dt/id :baby.pat.jes.dt/prim]
         :baby.pat.jes.dt/attrs    attrs})

(defn as-tag
  ([k] (as-tag k true))
  ([s v] {::id s
          ::vt (u/vt-dispatch v)
          ::tag v})
  ([s v m] (merge (as-tag s v) m)))

(def versionless (as-tag "version" "versionless"))

(def tags [versionless])
