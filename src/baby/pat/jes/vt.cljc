(ns baby.pat.jes.vt
  (:require [baby.pat.jes.vt.util.inflection :as inflection]
            [baby.pat.jes.vt.util.pred :as pred]
            [clojure.spec.alpha :as s]))

;; ### BASE SPECS
(s/def ::? boolean?)
(s/def ::any any?)
(s/def ::bool ::?)
(s/def ::coll coll?)
(s/def ::discard ::any)
(s/def ::double double?)
(s/def ::false false?)
(s/def ::fn fn?)
(s/def ::inst inst?)
(s/def ::int integer?)
(s/def ::kw keyword?)
(s/def ::long ::int)
(s/def ::map map?)
(s/def ::nil nil?)
(s/def ::num (s/or ::long ::long ::double ::double))
(s/def ::qkw (s/and ::kw qualified-keyword?))
(s/def ::seq seq?)
(s/def ::set set?)
(s/def ::str string?)
(s/def ::string ::str)
(s/def ::true true?)
(s/def ::vec vector?)
(s/def :db.type/boolean boolean?)
(s/def :db.type/double double?)
(s/def :db.type/instant inst?)
(s/def :db.type/keyword keyword?)
(s/def :db.type/long integer?)
(s/def :db.type/string string?)
;; END BASE SPECS

;; ### OPTIONALITY SPECS
(s/def ::?-or-nil (s/or ::? ::? ::nil ::nil))
(s/def ::atom-kw-or-str (s/or ::atom ::atom ::kw ::kw ::str ::str))
(s/def ::atom-kw-str-or-vec (s/or ::atom ::atom ::kw ::kw ::str ::str ::vec ::vec))
(s/def ::atom-kw-map-or-str (s/or ::atom ::atom ::kw ::kw ::map ::map ::str ::str))
(s/def ::atom-map-or-vec (s/or ::atom ::atom ::map ::map ::vec ::vec))
(s/def ::atom-or-fn (s/or ::atom ::atom ::fn ::fn))
(s/def ::atom-or-map (s/or ::atom ::atom ::map ::map))
(s/def ::atom-map-or-str (s/or ::atom ::atom ::map ::map ::str ::str))
(s/def ::atom-or-vec (s/or ::atom ::atom ::vec ::vec))
(s/def ::coll-or-nil (s/or ::coll ::coll ::nil ::nil))
(s/def ::coll-or-str (s/or ::coll ::coll ::str ::str))
(s/def ::double-or-nil (s/or ::double ::double ::nil ::nil))
(s/def ::fn-kw-or-str (s/or ::fn ::fn ::kw ::kw ::str ::str))
(s/def ::fn-or-kw (s/or ::fn ::fn ::kw ::kw))
(s/def ::fn-or-nil (s/or ::fn ::fn ::nil ::nil))
(s/def ::fn-or-str (s/or ::fn ::fn ::str ::str))
(s/def ::fn-or-vec (s/or ::fn ::fn ::vec ::vec))
(s/def ::fn-qkw-or-str (s/or ::qkw ::qkw ::str ::str ::fn ::fn))
(s/def ::kw-map-or-vec (s/or ::vec ::vec ::map ::map ::kw ::kw))
(s/def ::kw-map-or-str (s/or ::str ::str ::map ::map ::kw ::kw))
(s/def ::kw-map-set-or-vec (s/or ::kw ::kw ::map ::map ::set ::set ::vec ::vec))
(s/def ::kw-map-str-or-vec (s/or ::kw ::kw ::map ::map ::str ::str ::vec ::vec))
(s/def ::kw-nil-or-str (s/or ::kw ::kw ::nil ::nil ::str ::str))
(s/def ::kw-or-map (s/or ::kw ::kw ::map ::map))
(s/def ::kw-or-nil (s/or ::kw ::kw ::nil ::nil))
(s/def ::kw-or-str (s/or ::kw ::kw ::str ::str))
(s/def ::kw-set-or-vec (s/or ::kw ::kw ::set ::set ::vec ::vec))
(s/def ::kw-str-or-vec (s/or ::kw ::kw ::str ::str ::vec ::vec))
(s/def ::long-or-nil (s/or ::long ::long ::nil ::nil))
(s/def ::map-or-nil (s/or ::map ::map ::nil ::nil))
(s/def ::map-or-str (s/or ::map ::map ::str ::str))
(s/def ::map-or-vec (s/or ::vec ::vec ::map ::map))
(s/def ::num-or-nil (s/or ::num ::num ::nil ::nil))
(s/def ::qkw-or-nil (s/or ::qkw ::qkw ::nil ::nil))
(s/def ::qkw-or-str (s/or ::qkw ::qkw ::str ::str))
(s/def ::qkw-or-vec (s/or ::qkw ::qkw ::vec ::vec))
(s/def ::qkw-or-vec-of-qkws (s/or ::qkw ::qkw ::vec-of-qkws ::vec-of-qkws))
(s/def ::qkw-set-or-vec (s/or ::qkw ::qkw ::str ::str ::vec ::vec))
(s/def ::qkw-str-or-vec (s/or ::qkw ::qkw ::str ::str ::vec ::vec))
(s/def ::seq-or-nil (s/or ::seq ::seq ::nil ::nil))
(s/def ::set-or-nil (s/or ::set ::set ::nil ::nil))
(s/def ::set-or-vec (s/or ::set ::set ::vec ::vec))
(s/def ::str-or-nil (s/or ::str ::str ::nil ::nil))
(s/def ::vec-or-nil (s/or ::vec ::vec ::nil ::nil))
;; END OPTIONALITY SPECS

;; ### HOMOGENOUS SPECS
(s/def ::all-colls (s/and ::coll #(every? coll? %)))
(s/def ::all-doubles (s/and ::coll #(every? double? %)))
(s/def ::all-fns (s/and ::coll #(every? fn? %)))
(s/def ::all-kws (s/and ::coll #(every? keyword? %)))
(s/def ::all-longs (s/and ::coll #(every? integer? %)))
(s/def ::all-maps (s/and ::coll #(every? map? %)))
(s/def ::all-nils (s/and ::coll #(every? nil? %)))
(s/def ::all-nums (s/and ::coll #(every? number? %)))
(s/def ::all-qkws (s/and ::coll #(every? qualified-keyword? %)))
(s/def ::all-seqs (s/and ::coll #(every? seq? %)))
(s/def ::all-sets (s/and ::coll #(every? set? %)))
(s/def ::all-strs (s/and ::coll #(every? string? %)))
(s/def ::all-vecs (s/and ::coll #(every? vector? %)))
(s/def ::set-of-kws (s/and ::set ::all-kws))
(s/def ::set-of-qkws (s/and ::set ::all-qkws))
(s/def ::set-of-strs (s/and ::set ::all-strs))
(s/def ::vec-of-kws (s/and ::vec ::all-kws))
(s/def ::vec-of-maps (s/and ::vec ::all-maps))
(s/def ::vec-of-qkws (s/and ::vec ::all-qkws))
(s/def ::vec-of-strs (s/and ::vec ::all-strs))
(s/def ::vec-of-vecs (s/and ::vec ::all-vecs))
;; ### END homogenous

;; ### Start Extended
(s/def ::singular-str inflection/singular?)
(s/def ::plural-str inflection/plural?)
(s/def ::singular-kw inflection/singular-keyword?)
(s/def ::plural-kw inflection/plural-keyword?)
(s/def ::singular (s/or ::singular-str :singular-str ::singular-kw ::singular-kw))
(s/def ::plural (s/or ::plural-str :plural-str ::plural-kw ::plural-kw))
(s/def ::ukw (s/and ::kw pred/unqualified-keyword?))
(s/def ::ukw-or-nil (s/or ::ukw ::ukw ::nil ::nil))
(s/def ::all-ukws (s/and ::coll #(every? pred/unqualified-keyword? %)))
(s/def ::atom #(pred/atom? %))
(s/def ::atom-or-nil (s/or ::atom ::atom ::nil ::nil))
(s/def ::all-atoms (s/and ::coll #(every? pred/atom? %)))
(s/def ::idkw (s/and ::qkw #(pred/id-qkw? %)))
(s/def ::has-2 #(= 2 (count %)))
(s/def ::ident pred/entity-ident?)
(s/def ::idents pred/entity-idents?)
(s/def ::ident-or-idents pred/entity-ident-or-idents?)
(s/def ::ident-or-qkw (s/or ::ident ::ident ::qkw ::qkw))
(s/def ::ident-or-map (s/or ::ident ::ident ::map ::map))
(s/def ::ident-or-var-path (s/or ::ident ::ident ::var-path ::var-path))
(s/def ::ident-kw-or-str (s/or ::ident ::ident ::kw ::kw ::str ::str))
(s/def ::ident-map-or-qkw (s/or ::ident ::ident ::qkw ::qkw ::map ::map))
(s/def ::ident-idents-or-nil (s/or ::ident ::ident ::idents ::idents ::nil ::nil))
(s/def ::fn-ident-or-map (s/or ::fn ::fn ::ident ::ident ::map ::map))
(s/def ::fn-or-ident (s/or ::fn ::fn ::ident ::ident))
(s/def ::has-64 (s/and ::coll (fn [thing] (= 64 (count thing)))))
(s/def ::qid (s/and ::str ::has-64 ::str-without-numbers))
(s/def ::vt (s/and ::map #(pred/contains-keys? % ::id ::vt) #(clojure.string/includes? (namespace (::id %)) "vt")))
(s/def ::vt-id (s/and ::qkw #(clojure.string/includes? (namespace %) "vt")))
(s/def ::reentrant-lock #(pred/type-of? % "java.util.concurrent.locks.ReentrantLock"))
(s/def ::core-async-channel #(pred/type-of? % "clojure.core.async"))
(s/def ::bytes #?(:clj bytes? :cljs any?))
(s/def ::encrypted (pred/map-with? :data :iv))
(s/def ::file #(pred/type-of? % "java.io.File"))
(s/def ::vec-of-files (s/and ::vec (s/coll-of ::file)))
(s/def ::vec-of-2 (s/and ::vec ::has-2))
(s/def ::nm (s/and ::map #(pred/is-normalized? %)))
(s/def ::atom-or-nm (s/or ::atom ::atom ::nm ::nm))
(s/def ::atom-map-or-str (s/or ::atom ::atom ::map ::map ::str ::str))
(s/def ::atom-map-str-or-vec (s/or ::atom ::atom ::map ::map ::str ::str ::vec ::vec))
(s/def ::instant #?(:clj #(pred/type-of? % "java.time.Instant")
                    :cljs ::any))
(s/def ::time-pair (fn [x] (and (vector? x) (integer? (first x)) (keyword? (second x)))))
(s/def ::instant-or-long (s/or ::instant ::instant ::long ::long))
(s/def ::instant-or-num (s/or ::instant ::instant ::num ::num))
(s/def ::java-io-file #(pred/type-of? % "java.io.file"))
(s/def ::permissive-file (s/or ::str ::str ::java-io-file ::java-io-file))
(s/def ::long-or-qkw (s/or ::long ::long ::qkw ::qkw))