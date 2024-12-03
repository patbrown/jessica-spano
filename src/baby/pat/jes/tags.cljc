(ns baby.pat.jes.tags
  (:require [baby.pat.vt :refer [vt-dispatch]]))

(def id {:attr/id   :tag/id
         :attr/card :card/one
         :attr/vt   :vt/str})

(def vt {:attr/id   :tag/vt
         :attr/card :card/one
         :attr/vt   :vt/vt-id})

(def tag {:attr/id   :tag/tag
          :attr/card :card/one
          :attr/vt   :vt/any})

(defn tags-for [nmsp] {:attr/id   (keyword nmsp "tags")
                       :attr/card :card/many
                       :attr/vt   :vt/ident})

(def *tags (tags-for "tag"))
(def traits #:attr{:id :tag/traits, :card :card/many, :vt :vt/ident})

(def attrs [id vt tag *tags traits])

(def dt {:dt/id       :dt/tag
         :dt/parent   [:dt/id :dt/prim]
         :dt/attrs    attrs})

(defn as-tag
  ([k] (as-tag k true))
  ([s v] {:tag/id s
          :tag/vt (vt-dispatch v)
          :tag/tag v})
  ([s v m] (merge (as-tag s v) m)))

(def versionless (as-tag "version" "versionless"))

(def tags [versionless])
